package com.moodX.app.network.apis;

import com.moodX.app.models.home_content.AllCountry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CountryApi {

    @GET("all_country")
    Call<List<AllCountry>> getAllCountry(@Header("API-KEY") String apiKey,
                                         @Query("version") Integer vId,
                                         @Query("user_id") String userID);

}
