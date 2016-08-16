package com.example.bs148.coordinatelayoutwithtablayout;

/**
 * Created by BS148 on 8/16/2016.
 */
public class ContactsInformation {
    private String name;
    private String phoneNumber;

    public ContactsInformation(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
