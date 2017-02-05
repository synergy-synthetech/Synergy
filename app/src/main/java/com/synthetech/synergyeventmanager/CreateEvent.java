package com.synthetech.synergyeventmanager;

/**
 * Created by Aseem on 03-02-2017.
 */

public class CreateEvent {
    public String name, organisation, venue, date, website, creator_uid, email;

    public CreateEvent() {
        //Blank
    }

    public CreateEvent(String name, String organisation, String venue, String date, String website, String creator_uid, String email) {
        this.name = name;
        this.organisation = organisation;
        this.venue = venue;
        this.date = date;
        this.website = website;
        this.creator_uid = creator_uid;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getOrganisation() {
        return organisation;

    }

    public String getCreatorEmail() {
        return email;
    }

    public String getCreator_uid(){
        return creator_uid;
    }

    public String getUID(){
        String UID;
        UID = getName()+"_"+getCreator_uid();
        return UID;
    }

}
