LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := hello
LOCAL_SRC_FILES := hello.c

LOCAL_LDLIBS += -llog
LOCAL_LDLIBS += -lEGL

include $(BUILD_SHARED_LIBRARY)
