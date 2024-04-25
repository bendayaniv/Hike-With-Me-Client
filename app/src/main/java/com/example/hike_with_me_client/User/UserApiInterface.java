package com.example.hike_with_me_client.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApiInterface {

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{user_id}")
    Call<User> getUser(
            @Path("user_id") String user_id
    );

    @POST("users/addUser")
    Call<User> addUser(
            @Body User user
    );

    @PUT("users/{user_id}")
    Call<User> updateUser(
            @Path("user_id") String user_id,
            @Body User user
    );

    @DELETE("users/{user_id}")
    Call<String> deleteUser(
            @Path("user_id") String user_id
    );
}
