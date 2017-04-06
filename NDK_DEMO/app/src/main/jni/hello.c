//
// Created by pc on 2017/3/30.
//
#include<stdio.h>
#include<stdlib.h>
#include "binbin_leslie_cn_ndk_demo_MainActivity.h"

JNIEXPORT jstring JNICALL Java_binbin_leslie_cn_ndk_1demo_MainActivity_getStringFromC
  (JNIEnv * env, jclass jclazz){

    return (*env)->NewStringUTF(env,"Hello From JNI and C");
  }

