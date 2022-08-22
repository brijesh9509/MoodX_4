package com.moodX.app;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.moodX.app.network.RetrofitClient;
import com.moodX.app.network.apis.PassResetApi;
import com.moodX.app.network.model.PasswordReset;
import com.moodX.app.utils.RtlUtils;
import com.moodX.app.utils.ToastMsg;
import com.google.firebase.analytics.FirebaseAnalytics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PassResetActivity extends AppCompatActivity {
    private EditText etEmail;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RtlUtils.setScreenDirection(this);
        SharedPreferences sharedPreferences = getSharedPreferences("push", MODE_PRIVATE);
        boolean isDark = sharedPreferences.getBoolean("dark", false);

        if (isDark) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_reset);
        Toolbar toolbar = findViewById(R.id.toolbar);
        View backgroundView = findViewById(R.id.background_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //---analytics-----------
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "pass_reset_activity");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        etEmail = findViewById(R.id.email);
        Button btnReset = findViewById(R.id.reset_pass);

        if (isDark) {
            backgroundView.setBackgroundColor(getResources().getColor(R.color.nav_head_bg));
            btnReset.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_rounded_dark));
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);

        btnReset.setOnClickListener(v -> {
            if (!isValidEmailAddress(etEmail.getText().toString())) {
                new ToastMsg(PassResetActivity.this).toastIconError("please enter valid email");
            } else {
                resetPass(etEmail.getText().toString());
            }

        });
    }

    private void resetPass(String email) {
        dialog.show();
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        PassResetApi passResetApi = retrofit.create(PassResetApi.class);
        Call<PasswordReset> call = passResetApi.resetPassword(AppConfig.API_KEY, email, BuildConfig.VERSION_CODE);
        call.enqueue(new Callback<PasswordReset>() {
            @Override
            public void onResponse(@NonNull Call<PasswordReset> call, @NonNull Response<PasswordReset> response) {
                if (response.code() == 200) {
                    PasswordReset pr = response.body();
                    if (pr.getStatus().equals("success")) {
                        new ToastMsg(PassResetActivity.this).toastIconSuccess(pr.getMessage());
                        dialog.cancel();
                    } else {
                        new ToastMsg(PassResetActivity.this).toastIconError(pr.getMessage());
                        dialog.cancel();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PasswordReset> call, @NonNull Throwable t) {
                new ToastMsg(PassResetActivity.this).toastIconError("Something went wrong." + t.getMessage());
                dialog.cancel();
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


}
