#include <jni.h>
#include <string>

//RELEASE MoodX 5 LIVE
//std::string SERVER_URL = "Saylz5i7CLbAjaNAAXt4I39DasUrcxgW5lI8PfVft7s=]UXGL00JLJb1DSjqZznTwCw==]AvqTZS8ZIVz+gQ+CB1fh7C7YaijyoTSbyNFrAOVaCa5pquf+BbaSBcXXlqDNZ81P";


//RELEASE MoodX 6 POST
//std::string SERVER_URL = "fQ/9Se6b206wmEbqY3kfw840Ovx3OepVdbkvqpJUVpo=]SrE4XYS3+j9JBNqW2Qo/bw==]j3oWvcVoWy+Ef8tS8ouMa9WLszcK1iZnJJjzDtkdR80dQG5y9fgM9mCDCi8BSeTt";



std::string SERVER_URL = "https://www.moodx.vip/md_be/login/rest-api";

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