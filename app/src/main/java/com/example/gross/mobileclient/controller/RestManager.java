package com.example.gross.mobileclient.controller;

import com.example.gross.mobileclient.Callback.ApiService;
import com.example.gross.mobileclient.helper.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gross on 14.01.2017.
 */

public class RestManager {

    private ApiService mApiService;


    public ApiService getApiService(){
        if (mApiService == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mApiService = retrofit.create(ApiService.class);
        }
        return mApiService;
    }


}
