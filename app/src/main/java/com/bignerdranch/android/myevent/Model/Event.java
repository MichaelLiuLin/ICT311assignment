package com.bignerdranch.android.myevent.Model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by linliu on 23/9/17.
 */

public class Event {
    private UUID mId;
    private String mTitle;
    private String mPlace;

    public String getComments() {
        return mComments;
    }

    public void setComments(String comments) {
        mComments = comments;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    private String mComments;
    private String mDuration;
    private int mType;
    private Date mDate;
    public String mProperTime; // for report
    public String date2 = null;

    public String getGPS() {
        Log.e("mGPS","getGps() in event classx"+mGPS);
        return mGPS;
    }

    public void setGPS(String GPS) {
        mGPS = GPS;
        Log.e("mGPS","setGps() in event classx"+mGPS);
    }

    public String mGPS;
    public Event() {
        // Generate unique identifier
        this(UUID.randomUUID());
        //mId = UUID.randomUUID();
        //mDate = new Date();
    }

    public Event(UUID id) {
        mId = id;
        mDate = new Date();
    }



    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public Date getDate() {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        df.format("yyyy-MM-dd hh:mm:ss a", mDate);
        return mDate;
    }

    public String getProperTime(){
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        mProperTime="At "+df.format("hh:mm" ,mDate).toString()
                +" on "+df.format(getFormattedDate(),mDate).toString();
        Log.e("test",mProperTime);
        return mProperTime;
    }
    public String getFormattedDate(){
        SimpleDateFormat format1 = new SimpleDateFormat("d");
        String date = format1.format(mDate);//get day

        if(date.endsWith("1") && !date.endsWith("11"))
            date2 = "d'st' MMMM yyyy";
        else if(date.endsWith("2") && !date.endsWith("12"))
            date2 = "d'nd' MMMM yyyy";
        else if(date.endsWith("3") && !date.endsWith("13"))
            date2 = "d'rd' MMMM yyyy";
        else
            date2 = "d'th' MMMM yyyy";

        return date2;
    }

    public void setDate(Date date) {
        mDate = date;
    }





    public UUID getId() {
        return mId; }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    //photo thing
    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
