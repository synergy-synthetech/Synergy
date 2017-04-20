package com.synthetech.synergyeventmanager;

/**
 * Created by Aseem on 08-02-2017.
 */

public class Meeting {

    String topic;
    String calledBy;
    String eventUID;
    String creatorEmail;
    String venue;
    String dept;
    String date;
    //String time;

    public Meeting() {
    }

    public Meeting(String topic, String dept, String date, String calledBy, String eventUID) {
        this.topic = topic;
        this.date = date;
        this.dept = dept;
        this.calledBy = calledBy;
        this.eventUID = eventUID;
        //this.venue = venue;
        //this.time =time;
    }

    public String getTopic() {
        return topic;
    }

    public String getCalledBy() {
        return calledBy;
    }

    public String getEventUID() {
        return eventUID;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public String getDept() {
        return dept;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    /*public String getTime() {
        return time;
    }*/
}
