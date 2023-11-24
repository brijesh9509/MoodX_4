#include <jni.h>
#include <string>


//POST com.app.moodX
std::string SERVER_URL = "QfZf7+T+NeFcL1ryPSG94WjaCudXwBIgaqUXa515iek=]hCa/eADoFOqD+kJI5lk24w==]Lw8Kj6wWpmljjK6QMZVv1OuFPZSwSWjFDNB5K2rXkVTM5SjDIEsmrImH1LWNvH3u";

//RELEASE com.app.moodX
//std::string SERVER_URL = "hAadF+EsVkbIZEuYd9aGgA6ab4eAQ0SJNBB+ccKwnTg=]qO6qVYSBVnw21laYRILCIw==]eisd86ExF1mqFG0YTAFYAR/O3TWWTM6miIZwEgba4mjhtdKL7Nimpj4JFuT5eQzZ";


//std::string SERVER_URL = "https://www.moodx.vip/md_be/project/rest-api";

std::string PURCHASE_CODE = "xxxxxxxxxxxxxxx";

//std::string ONESIGNAL_APP_ID = "2c7cfedd-ec19-42bd-bf8c-fe2fa4fc6106"; //com.moodX.video
std::string ONESIGNAL_APP_ID = "a1882b56-1639-48e6-9564-afd0dbf8f84f";  //com.app.moodX


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