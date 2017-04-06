#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_binbin_leslie_cn_ndkdemo3_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    const char * result = "我是黄志斌呀,白痴";
    return env->NewStringUTF(result);
}
