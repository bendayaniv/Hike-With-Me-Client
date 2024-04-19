package com.example.hike_with_me_client;

import java.lang.ref.Cleaner;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MasterClass {
    static final String BASE_URL = "http://10.0.2.2:3000/";

    protected static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
