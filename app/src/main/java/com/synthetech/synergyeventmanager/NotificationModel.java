package com.synthetech.synergyeventmanager;

import java.util.Date;

/**
 * Created by Aseem on 20-04-2017.
 */

public class NotificationModel {

    String notificationText;
    long Time;

    public NotificationModel() {
        //Empty Constructor
    }

    public NotificationModel(String notificationText) {
        this.notificationText = notificationText;
        this.Time = new Date().getTime();
    }

    public String getNotificationText() {
        return notificationText;
    }

    public long getNotificationTime() {
        return Time;
    }
}
