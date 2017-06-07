ifeq ($(BOARD_BUILD_OP2_AUDIO),true)

MY_LOCAL_PATH := $(call my-dir)

$(info Welcome to the perfect audio party!)

ifneq ($(BOARD_USES_LEGACY_ALSA_AUDIO),true)
ifneq ($(filter mpq8092,$(TARGET_BOARD_PLATFORM)),)
include $(MY_LOCAL_PATH)/hal_mpq/Android.mk
else
include $(MY_LOCAL_PATH)/hal/Android.mk
endif
include $(MY_LOCAL_PATH)/voice_processing/Android.mk
include $(MY_LOCAL_PATH)/mm-audio/Android.mk
include $(MY_LOCAL_PATH)/policy_hal/Android.mk
include $(MY_LOCAL_PATH)/visualizer/Android.mk
include $(MY_LOCAL_PATH)/audiod/Android.mk
include $(MY_LOCAL_PATH)/post_proc/Android.mk
endif

endif