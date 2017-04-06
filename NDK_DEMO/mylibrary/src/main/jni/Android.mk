LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := hello
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	D:\AndroidProject\NDK_DEMO\mylibrary\src\main\jni\hello.c \
	D:\AndroidProject\NDK_DEMO\mylibrary\src\main\jni\hello1.c \

LOCAL_C_INCLUDES += D:\AndroidProject\NDK_DEMO\mylibrary\src\main\jni
LOCAL_C_INCLUDES += D:\AndroidProject\NDK_DEMO\mylibrary\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
