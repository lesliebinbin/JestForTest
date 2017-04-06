//
// Created by pc on 2017/3/30.
//
#include "binbin_leslie_cn_mylibrary_NdkString.h"

JNIEXPORT jstring JNICALL Java_binbin_leslie_cn_mylibrary_NdkString_getStringFromC
        (JNIEnv * env, jclass jclazz)
{
        return (*env)->NewStringUTF(env,"From C");
}