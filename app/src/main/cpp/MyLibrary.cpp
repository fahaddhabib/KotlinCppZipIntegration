#include <jni.h>
#include <iostream>

extern "C" JNIEXPORT void JNICALL
Java_com_example_testtask_MyLibrary_passMessage(JNIEnv* env, jobject obj, jstring message) {
    const char* nativeMessage = env->GetStringUTFChars(message, nullptr);
    std::cout << "Received message: " << nativeMessage << std::endl;
    env->ReleaseStringUTFChars(message, nativeMessage);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_testtask_MyLibrary_getMessage(JNIEnv* env, jobject obj) {
    return env->NewStringUTF("Message from C++");
}