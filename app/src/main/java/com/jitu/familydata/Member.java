package com.jitu.familydata;

/**
 * Created by JITU on 8/15/2017.
 */

public class Member {

    private String name;
    private int thumbnail;

    public Member(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
