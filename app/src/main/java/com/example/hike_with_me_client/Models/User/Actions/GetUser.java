package com.example.hike_with_me_client.Models.User.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.User.UserMasterClass;
import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_GetUser;
import com.example.hike_with_me_client.Models.User.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUser extends UserMasterClass {
    private final Callback_GetUser callback_getUser;

    public GetUser(Callback_GetUser callback_getUser) {
        this.callback_getUser = callback_getUser;
    }

    public void getUser(String userId) {
        Call<User> call = userApiInterface.getUser(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    callback_getUser.success(user);
                } else {
                    callback_getUser.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                callback_getUser.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
