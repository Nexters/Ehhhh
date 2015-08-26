package com.teamnexters.ehhhh.common;

/**
 * Created by 슬기 on 2015-08-25.
 */
public class Recommend {
    private String objectId;
    private String subject;
    private String pubId;
    public PubInfo pub;

    public Recommend() {
        this.pub = new PubInfo();
    }

    // getters & setters
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }
}