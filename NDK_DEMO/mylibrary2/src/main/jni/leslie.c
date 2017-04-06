//
// Created by pc on 2017/3/31.
//
#include "binbin_leslie_cn_mylibrary_LeslieJni.h"
#include <stdlib.h>
#include <stdio.h>
JNIEXPORT jstring JNICALL Java_binbin_leslie_cn_mylibrary_LeslieJni_getStringFromLesJni
        (JNIEnv * env, jclass jclazz){
    return (*env)->NewStringUTF(env,"From Leslie");
}
