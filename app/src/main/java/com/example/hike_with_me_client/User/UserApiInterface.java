package com.example.hike_with_me_client.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApiInterface {

    @GET("users")
    Call<List<User>> getAllUsers();
}
