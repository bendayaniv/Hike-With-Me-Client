package com.example.hike_with_me_client.Models.User.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Models.Objects.UserWithDistance;
import com.example.hike_with_me_client.Models.User.UserMasterClass;
import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_GetAllUsers;

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
        Call<List<UserWithDistance>> call = userApiInterface.getAllUsers(CurrentUser.getInstance().getUser().getId());

        call.enqueue(new Callback<List<UserWithDistance>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserWithDistance>> call, @NonNull Response<List<UserWithDistance>> response) {
                if(response.isSuccessful()) {
                    List<UserWithDistance> users = response.body();
                    callback_getAllUsers.success(users);
                } else {
                    callback_getAllUsers.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserWithDistance>> call, @NonNull Throwable t) {
                callback_getAllUsers.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
