package com.example.hike_with_me_client.Models.User.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_DeleteUser;
import com.example.hike_with_me_client.Models.User.UserMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteUser extends UserMasterClass {

    private final Callback_DeleteUser callback_deleteUser;

    public DeleteUser(Callback_DeleteUser callback_deleteUser) {
        this.callback_deleteUser = callback_deleteUser;
    }

    public void deleteUser(String userId) {
         Call<String> call = userApiInterface.deleteUser(userId);

         call.enqueue(new Callback<String>() {
             @Override
             public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                 if(response.isSuccessful()) {
                     String message = String.valueOf(response.body());
                     callback_deleteUser.success(message);
                 } else {
                     callback_deleteUser.error(String.valueOf(response.errorBody()));
                 }
             }

             @Override
             public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                 callback_deleteUser.error(t.getMessage());
                 t.printStackTrace();
             }
         });
    }
}
