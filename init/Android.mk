LOCAL_PATH := $(call my-dir)

ifeq ($(TARGET_DEVICE),oneplus2)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_C_INCLUDES := system/core/init
LOCAL_CFLAGS := -Wall -O3 -DANDROID_TARGET=\"$(TARGET_BOARD_PLATFORM)\"
LOCAL_SRC_FILES := init_oneplus2.cpp
LOCAL_MODULE := libinit_oneplus2
LOCAL_STATIC_LIBRARIES += libbase

LOCAL_STATIC_LIBRARIES := \
     libbase

include $(BUILD_STATIC_LIBRARY)

endif