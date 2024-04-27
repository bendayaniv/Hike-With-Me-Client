package com.example.hike_with_me_client.User.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.User.Callbacks.Callback_GetAllUsers;
import com.example.hike_with_me_client.User.User;
import com.example.hike_with_me_client.User.UserMasterClass;

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
        Call<List<User>> call = userApiInterface.getAllUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(response.isSuccessful()) {
                    List<User> users = response.body();
                    callback_getAllUsers.success(users);
                } else {
                    callback_getAllUsers.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                callback_getAllUsers.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
