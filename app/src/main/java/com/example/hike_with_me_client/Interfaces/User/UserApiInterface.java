package com.example.hike_with_me_client.Interfaces.User;

import com.example.hike_with_me_client.Models.Objects.UserWithDistance;
import com.example.hike_with_me_client.Models.User.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiInterface {

    @GET("users/getAllActiveUsers/{userId}")
    Call<List<UserWithDistance>> getAllUsers(
            @Path("userId") String userId
    );

    @GET("users/{user_id}")
    Call<User> getUser(
            @Path("user_id") String user_id
    );

    @POST("users/addUser")
    Call<User> addUser(
            @Body User user
    );

    @PUT("users")
    Call<User> updateUser(
            @Body User user
    );

    @DELETE("users/{user_id}")
    Call<String> deleteUser(
            @Path("user_id") String user_id
    );
}
