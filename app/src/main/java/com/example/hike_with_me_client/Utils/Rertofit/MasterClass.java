package com.example.hike_with_me_client.Utils.Rertofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MasterClass {
    static final String BASE_URL = "http://10.0.2.2:3000/";

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    protected static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
