#include <jni.h>
#include <iostream>
#include <string>

std::string storedMessage;

extern "C" JNIEXPORT void JNICALL
Java_com_example_testtask_MyLibrary_passMessage(JNIEnv* env, jobject obj, jstring message) {
    const char* nativeMessage = env->GetStringUTFChars(message, nullptr);
    storedMessage = nativeMessage;
    std::cout << "Received message: " << nativeMessage << std::endl;
    env->ReleaseStringUTFChars(message, nativeMessage);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_testtask_MyLibrary_getMessage(JNIEnv* env, jobject obj) {
    return env->NewStringUTF(storedMessage.c_str());
}
