#include <jni.h>
#include <string>
//DEBUG
std::string SERVER_URL = "4cGuSS5eYCXP0nXUfmvno//SL/Xo00eLXxlJIWjUwf4=]mKdSfSG3eBFRae3v8NR4zw==]SMMxxSHhJKvkwiNdUYMSJ0pqkKH0f8xdx7lFSfJHJfeXxlLe96MEeUjwlCGhOjPO";

//PRE RELAESE
//std::string SERVER_URL = "CstCKlXLcyCvuzDLQ3RP3hoi2jFCWvxRabITxdrDPjk=]35WVf8yCpeUG6x/4UfOlnw==]dABNBLYCOCUy4VjGUNhYCRUG390ECAR3SMKtgXRlS2Lt9QJ+HRYLTEaeO8d/Ssxq";

//RELEASE
//std::string SERVER_URL = "Saylz5i7CLbAjaNAAXt4I39DasUrcxgW5lI8PfVft7s=]UXGL00JLJb1DSjqZznTwCw==]AvqTZS8ZIVz+gQ+CB1fh7C7YaijyoTSbyNFrAOVaCa5pquf+BbaSBcXXlqDNZ81P";
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