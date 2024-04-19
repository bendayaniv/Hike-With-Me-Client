package com.example.hike_with_me_client.User.Actions;

import com.example.hike_with_me_client.User.Callbacks.Callback_AddUser;
import com.example.hike_with_me_client.User.User;
import com.example.hike_with_me_client.User.UserMasterClass;

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
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    callback_addUser.success(user);
                } else {
                    callback_addUser.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback_addUser.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
