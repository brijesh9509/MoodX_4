package com.moodX.app.network.apis;

import com.moodX.app.models.home_content.HomeContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface HomeContentApi {

    @GET("home_content_for_android")
    Call<HomeContent> getHomeContent(@Header("API-KEY") String apiKey,
                                     @Query("version") Integer id,
                                     @Query("user_id") String userId,
                                     @Query("udid") String uDID);
}
