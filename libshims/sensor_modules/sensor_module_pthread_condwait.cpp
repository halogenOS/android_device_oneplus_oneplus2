/*
 * Copyright (C) 2013 The Android Open Source Project
 * Copyright (C) 2017 halogenOS
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

#include <pthread.h>

#include <errno.h>
#include <limits.h>
#include <stdatomic.h>
#include <sys/mman.h>
#include <time.h>
#include <unistd.h>

#include "pthread_internal.h"

#include "private/bionic_futex.h"
#include "private/bionic_time_conversions.h"
#include "private/bionic_tls.h"

#define COND_SHARED_MASK 0x0001
#define COND_CLOCK_MASK 0x0002
#define COND_COUNTER_STEP 0x0004
#define COND_FLAGS_MASK (COND_SHARED_MASK | COND_CLOCK_MASK)
#define COND_COUNTER_MASK (~COND_FLAGS_MASK)

#define COND_IS_SHARED(c) (((c) & COND_SHARED_MASK) != 0)
#define COND_GET_CLOCK(c) (((c) & COND_CLOCK_MASK) >> 1)
#define COND_SET_CLOCK(attr, c) ((attr) | (c << 1))

struct pthread_cond_internal_t {
  atomic_uint state;

  bool process_shared() {
    return COND_IS_SHARED(atomic_load_explicit(&state, memory_order_relaxed));
  }

  bool use_realtime_clock() {
    return COND_GET_CLOCK(atomic_load_explicit(&state, memory_order_relaxed)) == CLOCK_REALTIME;
  }

#if defined(__LP64__)
  char __reserved[44];
#endif
};

static int __pthread_cond_timedwait_shim(pthread_cond_internal_t* cond, pthread_mutex_t* mutex,
                                    bool use_realtime_clock, const timespec* abs_timeout_or_null) {
  timespec* new_spec = const_cast<timespec*>(abs_timeout_or_null);
  if (abs_timeout_or_null != nullptr) {
    new_spec->tv_nsec = 0;
    new_spec->tv_sec += 1;
  }

  unsigned int old_state = atomic_load_explicit(&cond->state, memory_order_relaxed);
  pthread_mutex_unlock(mutex);
  int status = __futex_wait_ex(&cond->state, cond->process_shared(), old_state,
                               use_realtime_clock, new_spec);
  pthread_mutex_lock(mutex);

  if (status == -ETIMEDOUT) {
    return ETIMEDOUT;
  }
  return 0;
}

static int __pthread_cond_timedwait(pthread_cond_internal_t* cond, pthread_mutex_t* mutex,
                                    bool use_realtime_clock, const timespec* abs_timeout_or_null) {
  return __pthread_cond_timedwait_shim(cond, mutex, use_realtime_clock, abs_timeout_or_null);
}
