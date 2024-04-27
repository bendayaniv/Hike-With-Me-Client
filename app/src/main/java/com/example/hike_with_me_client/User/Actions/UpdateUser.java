package com.example.hike_with_me_client.User.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.User.Callbacks.Callback_UpdateUser;
import com.example.hike_with_me_client.User.User;
import com.example.hike_with_me_client.User.UserMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUser extends UserMasterClass {
    private final Callback_UpdateUser callback_updateUser;

    public UpdateUser(Callback_UpdateUser callback_updateUser) {
        this.callback_updateUser = callback_updateUser;
    }

    public void updateUser(User updatedUser) {
        Call<User> call = userApiInterface.updateUser(updatedUser);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    callback_updateUser.success(user);
                } else {
                    callback_updateUser.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                callback_updateUser.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
