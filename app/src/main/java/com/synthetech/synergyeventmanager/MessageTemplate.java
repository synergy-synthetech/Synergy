package com.synthetech.synergyeventmanager;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Aseem on 17-04-2017.
 */

public class MessageTemplate {

    String message;
    String user;
    long time;


    public MessageTemplate() {
    }

    public MessageTemplate(String message, String user) {
        this.message = message;
        this.user = user;
        time = new Date().getTime();
    }

    public String getMessage() {
        return message;
    }


    public String getUser() {
        return user;
    }
}

