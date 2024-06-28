package com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments;

import com.example.hike_with_me_client.Models.Objects.UserWithDistance;

public interface Callback_UserItem {
    void itemClicked(UserWithDistance user, int position);
}
