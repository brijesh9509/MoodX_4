package com.moodX.app.utils;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.moodX.app.AppConfig;
import com.moodX.app.LoginActivity;
import com.moodX.app.network.RetrofitClient;

import org.json.JSONObject;

public class ApiResources {

    public static String CURRENCY; // must be valid currency code
    public static String EXCHSNGE_RATE;
    public static String PAYPAL_CLIENT_ID;
    public static String RAZORPAY_EXCHANGE_RATE;
    public static String USER_PHONE;


    private static Toast toast;

    String termsURL = AppConfig.TERMS_URL;

    public String getTermsURL() {
        return termsURL;
    }


    public static void openLoginScreen(String mErrorBody,
                                       Context mContext) {
        try {
            JSONObject jObjError = new JSONObject(mErrorBody);
            if (toast != null ) {
                toast.cancel();
            }
            toast = Toast.makeText(mContext, jObjError.getString("message"), Toast.LENGTH_LONG);
            toast.show();

            SharedPreferences.Editor editor = mContext
                    .getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
            editor.putBoolean(Constants.USER_LOGIN_STATUS, false);
            editor.apply();

            /*DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
            databaseHelper.deleteUserData();*/

            PreferenceUtils.clearSubscriptionSavedData(mContext);

            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


