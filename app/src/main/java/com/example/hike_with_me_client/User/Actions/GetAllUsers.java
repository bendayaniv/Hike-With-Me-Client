package com.example.hike_with_me_client.User.Actions;

import android.util.Log;

import com.example.hike_with_me_client.User.Callbacks.Callback_GetAllUsers;
import com.example.hike_with_me_client.User.User;
import com.example.hike_with_me_client.User.UserMasterClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllUsers extends UserMasterClass {
    private final Callback_GetAllUsers callback_getAllUsers;

    public GetAllUsers(Callback_GetAllUsers callback_getAllUsers) {
        this.callback_getAllUsers = callback_getAllUsers;
    }

    public void getAllUsers() {
        Log.d("pttt", "GetAllUsers getAllUsers");
        Call<List<User>> call = userApiInterface.getAllUsers();
        Log.d("pttt", "GetAllUsers getAllUsers 2");

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    List<User> users = response.body();
                    callback_getAllUsers.success(users);
                } else {
                    callback_getAllUsers.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                callback_getAllUsers.error(t.getMessage());
                t.printStackTrace();
            }
        });
        Log.d("pttt", "GetAllUsers getAllUsers 3");
    }
}
