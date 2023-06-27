#include <jni.h>
#include <string>
//DEBUG
//std::string SERVER_URL = "4cGuSS5eYCXP0nXUfmvno//SL/Xo00eLXxlJIWjUwf4=]mKdSfSG3eBFRae3v8NR4zw==]SMMxxSHhJKvkwiNdUYMSJ0pqkKH0f8xdx7lFSfJHJfeXxlLe96MEeUjwlCGhOjPO";
//POST RELAESE
//std::string SERVER_URL = "eWxKbz6591cOuHs2j19IlfvOJ4q7A1ExtEzTIfrNrqc=]URcbkVEkUJEMuy7whQz9sw==]K3hJOhVQOiQV2KSo7M8aFgbjwU2Yvs8mIai/Dx/oun0S7T/pviwYWPCdjIwDXGJc";
//RELEASE
std::string SERVER_URL = "xXsONpX7mCR7nVSw1DUJuXjLUmtnWfFWlHIeEeQgMpQ=]Yfpg2daTngLoA+mU+9dU9w==]ezSfM2PHn9BQNeIY3MDUGHiSeRdvf2zHto1Q2gIk+3/Kki+9fwItmoENqtOZgYv3";
//std::string SERVER_URL = "https://www.moodx.vip/md_be/login/rest-api";

std::string PURCHASE_CODE = "xxxxxxxxxxxxxxx";
std::string ONESIGNAL_APP_ID = "2c7cfedd-ec19-42bd-bf8c-fe2fa4fc6106";


//WARNING: ==>> Don't change anything below.
extern "C" JNIEXPORT jstring JNICALL
Java_com_moodX_app_AppConfig_getApiServerUrl(
        JNIEnv *env,
        jclass clazz) {
    return env->NewStringUTF(SERVER_URL.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_moodX_app_AppConfig_getPurchaseCode(
        JNIEnv *env,
        jclass clazz) {
    return env->NewStringUTF(PURCHASE_CODE.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_moodX_app_AppConfig_getOneSignalAppID(
        JNIEnv *env,
        jclass clazz) {
    return env->NewStringUTF(ONESIGNAL_APP_ID.c_str());
}