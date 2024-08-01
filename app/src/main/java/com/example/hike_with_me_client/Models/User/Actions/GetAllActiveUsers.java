package com.example.hike_with_me_client.Models.User.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;
import com.example.hike_with_me_client.Models.Objects.UserWithDistance;
import com.example.hike_with_me_client.Models.User.UserMasterClass;
import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_GetAllUsers;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllActiveUsers extends UserMasterClass {
    private final Callback_GetAllUsers callback_getAllUsers;

    public GetAllActiveUsers(Callback_GetAllUsers callback_getAllUsers) {
        this.callback_getAllUsers = callback_getAllUsers;
    }

    public void getAllActiveUsers() {
        Call<List<UserWithDistance>> call = userApiInterface.getAllActiveUsers(CurrentUser.getInstance().getUser().getId());

        call.enqueue(new Callback<List<UserWithDistance>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserWithDistance>> call, @NonNull Response<List<UserWithDistance>> response) {
                if (response.isSuccessful()) {
                    List<UserWithDistance> users = response.body();
                    callback_getAllUsers.success(users);
                } else {
                    ResponseBody errorBody = response.errorBody();
                    try {
                        assert errorBody != null;
                        String errorMessage = errorBody.string();
                        callback_getAllUsers.error(errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
