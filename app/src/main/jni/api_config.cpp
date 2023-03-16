#include <jni.h>
#include <string>


std::string SERVER_URL = "https://www.moodx.vip/md_be/login/rest-api";
//std::string SERVER_URL = "https://phpstack-781815-3329915.cloudwaysapps.com/md_be/login/rest-api";

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