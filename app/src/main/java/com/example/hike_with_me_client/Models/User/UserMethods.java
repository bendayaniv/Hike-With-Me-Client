package com.example.hike_with_me_client.Models.User;

import android.util.Log;

import com.example.hike_with_me_client.Models.Objects.UserWithDistance;
import com.example.hike_with_me_client.Models.Trip.TripMethods;
import com.example.hike_with_me_client.Models.User.Actions.AddUser;
import com.example.hike_with_me_client.Models.User.Actions.DeleteUser;
import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_AddUser;
import com.example.hike_with_me_client.Models.User.Actions.GetAllActiveUsers;
import com.example.hike_with_me_client.Models.User.Actions.GetUser;
import com.example.hike_with_me_client.Models.User.Actions.UpdateUser;
import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_DeleteUser;
import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_GetAllUsers;
import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_GetUser;
import com.example.hike_with_me_client.Interfaces.User.Callbacks.Callback_UpdateUser;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.CurrentUser;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ErrorMessageFromServer;

import java.util.ArrayList;
import java.util.List;

public class UserMethods {

    public static void getAllUsers() {
        Callback_GetAllUsers callback_getAllUsers = new Callback_GetAllUsers() {
            @Override
            public void success(List<UserWithDistance> users) {
                if(users.isEmpty()) {
                    Log.d("UserMethods", "No users found");
                } else {
                    Log.d("UserMethods", "Users found: " + users);
                }
                CurrentUser.getInstance().setUsersWithDistance((ArrayList<UserWithDistance>) users);
            }

            @Override
            public void error(String message) {
                Log.d("UserMethods", "Error - all users: " + message);
                ErrorMessageFromServer.getInstance().setErrorMessageFromServer(message);
            }
        };
        new GetAllActiveUsers(callback_getAllUsers).getAllActiveUsers();
    }

    public static void getSpecificUser(String userId) {
        Callback_GetUser callback_getUser = new Callback_GetUser() {
            @Override
            public void success(User user) {
                Log.d("UserMethods", "User found: " + user);

                // Set CurrentUser
                CurrentUser.getInstance().setUser(user);
                CurrentUser.getInstance().getUser().setActive(true);
                CurrentUser.getInstance().setActiveTrips(new ArrayList<>());
                TripMethods.getTripsByUser();
            }

            @Override
            public void error(String message) {
                Log.d("UserMethods", "Error - specific user: " + message);
            }
        };
        new GetUser(callback_getUser).getUser(userId);
    }

    public static void addUser(User user) {
        Callback_AddUser callback_addUser = new Callback_AddUser() {
            @Override
            public void success(User user) {
                Log.d("UserMethods", "User added: " + user);

                // Set CurrentUser
                CurrentUser.getInstance().setUser(user);
            }

            @Override
            public void error(String message) {
                Log.d("UserMethods", "Error - add user: " + message);
            }
        };
        new AddUser(callback_addUser).addUser(user);
    }

    public static void updateUser(User updatedUser) {
        Callback_UpdateUser callback_updateUser = new Callback_UpdateUser() {
            @Override
            public void success(User user) {
                Log.d("UserMethods", "User updated: " + user);
                // Set CurrentUser
                CurrentUser.getInstance().setUser(user);
            }

            @Override
            public void error(String message) {
                Log.d("UserMethods", "Error - update user: " + message);
            }
        };
        new UpdateUser(callback_updateUser).updateUser(updatedUser);
    }

    public static void deleteUser(String userId) {
        Callback_DeleteUser callback_deleteUser = new Callback_DeleteUser() {
            @Override
            public void success(String message) {
                Log.d("UserMethods", "Deleting user: " + message);
                CurrentUser.getInstance().removeUser();
            }

            @Override
            public void error(String message) {
                Log.d("UserMethods", "Error - delete user: " + message);
            }
        };
        new DeleteUser(callback_deleteUser).deleteUser(userId);
    }
}
