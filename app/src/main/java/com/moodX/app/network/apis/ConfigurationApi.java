package com.moodX.app.network.apis;

import com.moodX.app.network.model.config.Configuration;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ConfigurationApi {
    @GET("config")
    Call<Configuration> getConfigurationData(@Header("API-KEY") String apiKey,
                                             @Query("version") Integer id,
                                             @Query("user_id") String userId
                                             /*@Query("udid") String uDID*/);
}
