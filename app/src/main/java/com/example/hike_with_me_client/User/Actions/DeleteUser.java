package com.example.hike_with_me_client.User.Actions;

import com.example.hike_with_me_client.User.Callbacks.Callback_DeleteUser;
import com.example.hike_with_me_client.User.User;
import com.example.hike_with_me_client.User.UserMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteUser extends UserMasterClass {

    private final Callback_DeleteUser callback_deleteUser;

    public DeleteUser(Callback_DeleteUser callback_deleteUser) {
        this.callback_deleteUser = callback_deleteUser;
    }

    public void deleteUser(String userId) {
         Call<User> call = userApiInterface.deleteUser(userId);

         call.enqueue(new Callback<User>() {
             @Override
             public void onResponse(Call<User> call, Response<User> response) {
                 if(response.isSuccessful()) {
                     User user = response.body();
                     callback_deleteUser.success(user);
                 } else {
                     callback_deleteUser.error("" + response.errorBody());
                 }
             }

             @Override
             public void onFailure(Call<User> call, Throwable t) {
                 callback_deleteUser.error(t.getMessage());
                 t.printStackTrace();
             }
         });
    }
}
