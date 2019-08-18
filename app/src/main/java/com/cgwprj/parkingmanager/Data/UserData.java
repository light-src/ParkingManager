package com.cgwprj.parkingmanager.Data;

import com.google.firebase.auth.FirebaseUser;

public class UserData {
    private UserData(){}

    private static UserData instance = null;
    private String userIdHash;
    private FirebaseUser user;

    public static UserData getInstance(){
        if(instance != null)
            return instance;
        else
            instance = new UserData();

        return instance;
    }

    public void setUserIdHash(String userIdHash) {
        this.userIdHash = userIdHash;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }
}
