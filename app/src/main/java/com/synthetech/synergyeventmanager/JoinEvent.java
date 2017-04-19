package com.synthetech.synergyeventmanager;

/**
 * Created by Aseem on 09-02-2017.
 */

public class JoinEvent {
    public String memberEmail;

    public JoinEvent() {
    }

    public JoinEvent(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberEmail(){
        return memberEmail;
    }
}
