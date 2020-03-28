package com.hadi123.www.bloodbankgcek;

public class Users {
    private  String name;
    private  String Bloodgroup;
    private  String Donation;
    private  String mobile_number;

    public Users(String name, String bloodgroup, String donation, String mobile_number) {
        this.name = name;
        Bloodgroup = bloodgroup;
        Donation = donation;
        this.mobile_number = mobile_number;
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

    public String getDonation() {
        return Donation;
    }

    public void setDonation(String donation) {
        Donation = donation;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
}

