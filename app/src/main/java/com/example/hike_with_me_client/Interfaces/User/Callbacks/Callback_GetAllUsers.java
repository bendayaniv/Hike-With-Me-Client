package com.example.hike_with_me_client.Interfaces.User.Callbacks;

import com.example.hike_with_me_client.Models.Objects.UserWithDistance;
import com.example.hike_with_me_client.Models.User.User;

import java.util.List;

public interface Callback_GetAllUsers {
    void success(List<UserWithDistance> tasks);
    void error(String error);
}
