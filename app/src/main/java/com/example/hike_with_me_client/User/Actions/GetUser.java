package com.example.hike_with_me_client.User.Actions;

import android.util.Log;

import com.example.hike_with_me_client.User.Callbacks.Callback_GetUser;
import com.example.hike_with_me_client.User.User;
import com.example.hike_with_me_client.User.UserMasterClass;

import java.util.List;

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
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    callback_getUser.success(user);
                } else {
                    callback_getUser.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback_getUser.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
