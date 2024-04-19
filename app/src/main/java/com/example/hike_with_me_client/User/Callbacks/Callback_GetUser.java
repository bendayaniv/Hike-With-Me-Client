package com.example.hike_with_me_client.User.Callbacks;

import com.example.hike_with_me_client.User.User;

import java.util.List;

public interface Callback_GetUser {
    void success(User user);
    void error(String error);
}
