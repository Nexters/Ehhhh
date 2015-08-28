package com.teamnexters.ehhhh.common;

/**
 * Created by 슬기 on 2015-08-25.
 */
public class PubInfo {
    private String objectId;
    private String name;
    private String nameEng;
    private String phone;
    private String district;    //구,구분
    private String adress;
    private String time;        //영업시간
    private String info1;       //요약설명
    private String info2;       //상세설명
    private String etc;         //기타정보(휴무일 등)

    private boolean bookmarkYN;

    private int count;          //구별 카운트

    public PubInfo() {
    }

    // getters & setters
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public boolean getBookmarkYN() {
        if (bookmarkYN)
            return bookmarkYN;
        else
            return false;
    }

    public void setBookmarkYN(boolean bookmarkYN) {
        this.bookmarkYN = bookmarkYN;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}