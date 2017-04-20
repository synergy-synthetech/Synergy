package com.synthetech.synergyeventmanager;

/**
 * Created by Aseem on 03-02-2017.
 */

public class UserInformation {

    public String name;
    public String phone;
    public String email;
    public String uid;

    public UserInformation(){

    }

    public UserInformation(String name, String phone, String email, String uid) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
            return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
