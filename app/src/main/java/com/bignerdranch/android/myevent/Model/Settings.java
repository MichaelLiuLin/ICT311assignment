package com.bignerdranch.android.myevent.Model;

import android.support.v4.app.FragmentActivity;

/**
 * Created by linliu on 25/10/17.
 */

public class Settings {

    private String mId;
    private String mName;
    private String mIdNum;
    private String mEmail;
    private String mGender;
    private String mComment;

    private static final String SETTING_INDEX = "1";

    //constructor
    public Settings() {
        this(SETTING_INDEX);
    }

    public Settings(String mId) {
        this.mId = mId;
    }

    public String getId() {
        return mId;
    }

    public String getIdNum() {
        return mIdNum;
    }

    public void setIdNum(String mIdNum) {
        this.mIdNum = mIdNum;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }


}

















