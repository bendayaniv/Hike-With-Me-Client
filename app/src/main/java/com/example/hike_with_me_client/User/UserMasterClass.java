package com.example.hike_with_me_client.User;

import com.example.hike_with_me_client.MasterClass;

public class UserMasterClass extends MasterClass {

    public UserApiInterface userApiInterface = MasterClass.retrofit.create(UserApiInterface.class);
}
