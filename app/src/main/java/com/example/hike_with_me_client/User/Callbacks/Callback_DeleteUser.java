package com.example.hike_with_me_client.User.Callbacks;

import com.example.hike_with_me_client.User.User;

public interface Callback_DeleteUser {
    void success(User user);
    void error(String error);
}
