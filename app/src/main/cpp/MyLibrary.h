#ifndef MYLIBRARY_H
#define MYLIBRARY_H

#include <jni.h>

extern "C" {
JNIEXPORT void JNICALL Java_com_example_testtask_MyLibrary_passMessage(JNIEnv* env, jobject obj, jstring message);
JNIEXPORT jstring JNICALL Java_com_example_testtask_MyLibrary_getMessage(JNIEnv* env, jobject obj);
}

#endif // MYLIBRARY_H
