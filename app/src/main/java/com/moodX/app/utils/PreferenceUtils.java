package com.moodX.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.moodX.app.AppConfig;
import com.moodX.app.database.DatabaseHelper;
import com.moodX.app.network.RetrofitClient;
import com.moodX.app.network.apis.SubscriptionApi;
import com.moodX.app.network.model.ActiveStatus;
import com.moodX.app.BuildConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PreferenceUtils {
    public static final String TAG = "PreferenceUtils";


    public static boolean isActivePlan(Activity context) {
        String status = getSubscriptionStatus(context);
        if (status != null) {
            return status.equals("active");
        } else {
            return false;
        }
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.USER_LOGIN_STATUS, Context.MODE_PRIVATE);
        return preferences.getBoolean(Constants.USER_LOGIN_STATUS, false);
    }

    public static boolean isMandatoryLogin(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getConfigurationData().getAppConfig().getMandatoryLogin();
    }

    public static String getSubscriptionStatus(Activity context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getActiveStatusData().getStatus();
    }

    public static long getCurrentTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String currentDateAndTime = sdf.format(new Date());

        Date date = null;
        try {
            date = sdf.parse(currentDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getTimeInMillis();
    }

    public static long getExpireTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String currentDateAndTime = sdf.format(new Date());

        Date date = null;
        try {
            date = sdf.parse(currentDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 2);

        return calendar.getTimeInMillis();
    }

    public static boolean isValid(Context context) {
        String savedTime = getUpdateTime(context);
        long currentTime = getCurrentTime();
        return Long.parseLong(savedTime) > currentTime;
    }

    private static String getUpdateTime(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return String.valueOf(db.getActiveStatusData().getExpireTime());
    }

    public static void updateSubscriptionStatus(final Context context) {
        //get saved user id
        String userId = getUserId(context);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        SubscriptionApi subscriptionApi = retrofit.create(SubscriptionApi.class);

        Call<ActiveStatus> call = subscriptionApi.getActiveStatus(MyAppClass.API_KEY,
                userId, BuildConfig.VERSION_CODE,Constants.getDeviceId(context));
        call.enqueue(new Callback<ActiveStatus>() {
            @Override
            public void onResponse(@NonNull Call<ActiveStatus> call, @NonNull Response<ActiveStatus> response) {
                if (response.code() == 200) {
                    ActiveStatus activeStatus = response.body();
                    DatabaseHelper db = new DatabaseHelper(context);
                    db.deleteAllActiveStatusData();
                    db.insertActiveStatusData(activeStatus);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ActiveStatus> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void clearSubscriptionSavedData(Context context) {
        //now save to sharedPreference
        DatabaseHelper db = new DatabaseHelper(context);
        db.deleteAllActiveStatusData();
    }

    public static String getUserId(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getUserData().getUserId();
    }

    public static boolean isProgramGuideEnabled(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.getConfigurationData().getAppConfig().getProgramGuideEnable();
    }


}
