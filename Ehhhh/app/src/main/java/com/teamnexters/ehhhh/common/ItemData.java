package com.teamnexters.ehhhh.common;

/**
 * Created by cha on 2015-08-11.
 */
public class ItemData {
    private String pubName;
    private String address;
    private String phone;

    public ItemData(String pubName, String address, String phone) {
        this.pubName = pubName;
        this.address = address;
        this.phone = phone;
    }

    // getters & setters
    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}