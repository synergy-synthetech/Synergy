package com.synthetech.synergyeventmanager;

/**
 * Created by Aseem on 08-02-2017.
 */

public class Meeting {

    String topic;
    String calledBy;
    String eventName;
    String creatorEmail;
    String venue;
    String scope;
    String date;
    String time;

    public Meeting() {
    }

    public Meeting(String topic, String calledBy, String eventName, String creatorEmail, String venue, String scope, String date, String time) {
        this.topic = topic;
        this.calledBy = calledBy;
        this.eventName = eventName;
        this.creatorEmail = creatorEmail;
        this.venue = venue;
        this.scope = scope;
        this.date = date;
        this.time =time;
    }

    public String getTopic() {
        return topic;
    }

    public String getCalledBy() {
        return calledBy;
    }

    public String getEventName() {
        return eventName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public String getScope() {
        return scope;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
