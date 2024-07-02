package com.example.hike_with_me_client.Models.User;

import com.example.hike_with_me_client.Interfaces.User.UserApiInterface;
import com.example.hike_with_me_client.Utils.Rertofit.MasterClass;

public class UserMasterClass extends MasterClass {

    public UserApiInterface userApiInterface = MasterClass.retrofit.create(UserApiInterface.class);
}
