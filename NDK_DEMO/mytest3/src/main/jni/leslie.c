//
// Created by pc on 2017/3/31.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "binbin_leslie_cn_myapplication_MainActivity.h"

// for __android_log_print(ANDROID_LOG_INFO, "YourApp", "formatted message");
#include <android/log.h>
#define TAG "Leslie"
#define LOGV(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)


JNIEXPORT void JNICALL Java_binbin_leslie_cn_myapplication_MainActivity_updateFile
(JNIEnv * env, jclass jclazz, jstring jstr){

    //生成Native层的char类型指针
    const char * file_path = (*env)->GetStringUTFChars(env,jstr,NULL);
    if(file_path!=NULL){
        LOGV("from c file_path %s",file_path);
    //打开文件
    }
    FILE * file = fopen(file_path,"a+");
    if(file!=NULL){
        LOGV("from c open file success");
    }

    char data[] = "I am a boy";
    //写入文件
    int count = fwrite(data,strlen(data),1,file);
    if(count>0){
        LOGV("from c write file success");
    }

    if(file!=NULL){
        fclose(file);
    }
    (*env)->ReleaseStringUTFChars(env,jstr,file_path);
}


JNIEXPORT jintArray JNICALL Java_binbin_leslie_cn_myapplication_MainActivity_updateIntArray
        (JNIEnv * env, jclass jclazz, jintArray jintArr){
        jint nativeArray[5];
        (*env)->GetIntArrayRegion(env,jintArr,0,5,nativeArray);

        int j;
        for(j=0;j<5;j++){
            nativeArray[j]+=5;
            LOGV("from c int %d",nativeArray[j]);
        }
        (*env)->SetIntArrayRegion(env,jintArr,0,5,nativeArray);
        return jintArr;
}

JNIEXPORT jintArray JNICALL Java_binbin_leslie_cn_myapplication_MainActivity_updateIntArray2
        (JNIEnv *env, jclass jclazz, jintArray jintArr){
    jint* data = (*env)->GetIntArrayElements(env,jintArr,NULL);
    jsize len = (*env)->GetArrayLength(env,jintArr);

    int j;
    for(j=0;j<len;j++){
        data[j]+=3;
        LOGV("from c2 int %d",data[j]);
    }
    (*env)->ReleaseIntArrayElements(env,jintArr,data,0);
    return jintArr;
}