package com.example.sns_study;

import android.widget.EditText;

public class MemberInfo {
    private String name;
    private String PhoneNumber;
    private String Birthday;
    private String Address;

    public MemberInfo(String name, String phoneNumber, String birthday, String address) {
        this.name = name;
        this.PhoneNumber = phoneNumber;
        this.Birthday = birthday;
        this.Address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
