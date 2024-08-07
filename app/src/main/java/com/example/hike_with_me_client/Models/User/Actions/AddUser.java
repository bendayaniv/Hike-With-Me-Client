package com.example.hike_with_me_client.Models.User.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_AddUser;
import com.example.hike_with_me_client.Models.User.User;
import com.example.hike_with_me_client.Models.User.UserMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends UserMasterClass {
    private final Callback_AddUser callback_addUser;

    public AddUser(Callback_AddUser callback_addUser) {
        this.callback_addUser = callback_addUser;
    }

    public void addUser(User user) {
        Call<User> call = userApiInterface.addUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    callback_addUser.success(user);
                } else {
                    callback_addUser.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                callback_addUser.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
