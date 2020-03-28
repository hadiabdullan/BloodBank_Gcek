package com.hadi123.www.bloodbankgcek;

public class Users {
    private  String name;
    private  String Bloodgroup;

    public Users(String name, String bloodgroup) {
        this.name = name;
        Bloodgroup = bloodgroup;
    }

    public Users() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodgroup() {
        return Bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        Bloodgroup = bloodgroup;
    }
}

