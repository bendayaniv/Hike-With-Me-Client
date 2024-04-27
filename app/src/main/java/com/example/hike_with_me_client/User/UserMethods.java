package com.example.hike_with_me_client.User;

import android.util.Log;

import com.example.hike_with_me_client.User.Actions.AddUser;
import com.example.hike_with_me_client.User.Actions.DeleteUser;
import com.example.hike_with_me_client.User.Actions.GetAllUsers;
import com.example.hike_with_me_client.User.Actions.GetUser;
import com.example.hike_with_me_client.User.Actions.UpdateUser;
import com.example.hike_with_me_client.User.Callbacks.Callback_AddUser;
import com.example.hike_with_me_client.User.Callbacks.Callback_DeleteUser;
import com.example.hike_with_me_client.User.Callbacks.Callback_GetAllUsers;
import com.example.hike_with_me_client.User.Callbacks.Callback_GetUser;
import com.example.hike_with_me_client.User.Callbacks.Callback_UpdateUser;
import com.example.hike_with_me_client.Utils.CurrentUser;

import java.util.List;

public class UserMethods {

    public static void getAllUsers() {
        Callback_GetAllUsers callback_getAllUsers = new Callback_GetAllUsers() {
            @Override
            public void success(List<User> users) {
                if(users.size() == 0) {
                    Log.d("pttt", "No users found");
                } else {
                    Log.d("pttt", "Users found: " + users);
                }
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
            }
        };
        new GetAllUsers(callback_getAllUsers).getAllUsers();
    }

    public static void getSpecificUser(String userId) {
        Callback_GetUser callback_getUser = new Callback_GetUser() {
            @Override
            public void success(User user) {
                Log.d("pttt", "User found: " + user);

                // Set CurrentUser
                CurrentUser.getInstance().setUser(user);
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
            }
        };
        new GetUser(callback_getUser).getUser(userId);
    }

    public static void addUser(User user) {
        Callback_AddUser callback_addUser = new Callback_AddUser() {
            @Override
            public void success(User user) {
                Log.d("pttt", "User added: " + user);

                // Set CurrentUser
                CurrentUser.getInstance().setUser(user);
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
            }
        };
        new AddUser(callback_addUser).addUser(user);
    }

    public static void updateUser(User updatedUser) {
        Callback_UpdateUser callback_updateUser = new Callback_UpdateUser() {
            @Override
            public void success(User user) {
                Log.d("pttt", "User updated: " + user);
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
            }
        };
        new UpdateUser(callback_updateUser).updateUser(updatedUser);
    }

    public static void deleteUser(String userId) {
        Callback_DeleteUser callback_deleteUser = new Callback_DeleteUser() {
            @Override
            public void success(String message) {
                Log.d("pttt", "Deleting user: " + message);
                CurrentUser.getInstance().removeUser();
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
            }
        };
        new DeleteUser(callback_deleteUser).deleteUser(userId);
    }
}
