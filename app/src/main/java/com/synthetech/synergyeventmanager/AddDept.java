package com.synthetech.synergyeventmanager;

/**
 * Created by Aseem on 03-04-2017.
 */

public class AddDept {
    String dept;
    String leader;

    public AddDept() {
    }

    public AddDept(String dept){
        this.dept = dept;
    }

    public String getDept() {
        return dept;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}
