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

    @GET("hike-with-me/users/getAllActiveUsers/{userId}")
    Call<List<UserWithDistance>> getAllUsers(
            @Path("userId") String userId
    );

    @GET("hike-with-me/users/{user_id}")
    Call<User> getUser(
            @Path("user_id") String user_id
    );

    @POST("hike-with-me/users/addUser")
    Call<User> addUser(
            @Body User user
    );

    @PUT("hike-with-me/users")
    Call<User> updateUser(
            @Body User user
    );

    @DELETE("hike-with-me/users/{user_id}")
    Call<String> deleteUser(
            @Path("user_id") String user_id
    );
}
