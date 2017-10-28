LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_C_INCLUDES := system/core/init external/selinux/libselinux/include
LOCAL_CFLAGS := -Wall -DANDROID_TARGET=\"$(TARGET_BOARD_PLATFORM)\"
LOCAL_SRC_FILES := init_oneplus2.cpp
LOCAL_MODULE := libinit_oneplus2

LOCAL_STATIC_LIBRARIES := \
     libbase \
     libselinux

include $(BUILD_STATIC_LIBRARY)
