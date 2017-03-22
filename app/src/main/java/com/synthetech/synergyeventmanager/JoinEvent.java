package com.synthetech.synergyeventmanager;

/**
 * Created by Aseem on 09-02-2017.
 */

public class JoinEvent {
    public String email;

    public JoinEvent() {
    }

    public JoinEvent(String email) {
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
}
