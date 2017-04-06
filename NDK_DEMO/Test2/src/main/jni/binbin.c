//
// Created by pc on 2017/3/31.
//

#include "binbin_leslie_cn_myapplication_MainActivity.h"
#include <stdlib.h>
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_binbin_leslie_cn_myapplication_MainActivity_getHelloFromBinbin
        (JNIEnv * jniEnv, jclass jclazz){
    return (*jniEnv)->NewStringUTF(jniEnv,"Hello Binbin");
}
