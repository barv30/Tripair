package com.example.recycleViewPack;

import java.io.Serializable;

public class ContactPOJO implements Serializable {

    private String mName;
    private String mDateDest;
    private String mDateLeft;
    private String mSmoking;
    private int mAge;
    private String mImage;


    public ContactPOJO() {}
    public ContactPOJO(String firstName,String lastName, int aday,int amonth,int ayear,
                       int lday,int lmonth,int lyear,boolean isSmoking,int age, String image) {
        mName =firstName +" " +lastName ;
        mDateDest= aday+"."+amonth+"."+ayear;
        if(lday == 0 && lmonth == 0 && lyear==0)
        {
            mDateLeft = "Unknown";
        }
        else
        {
            mDateLeft = lday+"."+lmonth+"."+lyear;
        }
        if(isSmoking)
        {
            mSmoking = "Yes";
        }
        else
        {
            mSmoking="No";
        }
        mAge=age;
        mImage=image;

    }

    public String getmImage() {
        return mImage;
    }

    public String getmDateLeft() {
        return mDateLeft;
    }

    public String getmName() {
        return mName;
    }

    public int getmAge() {
        return mAge;
    }

    public String getmDateDest() {
        return mDateDest;
    }

    public String getmSmoking() {
        return mSmoking;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmDateLeft(String mDateLeft) {
        this.mDateLeft = mDateLeft;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public void setmDateDest(String mDateDest) {
        this.mDateDest = mDateDest;
    }

    public void setmSmoking(String mSmoking) {
        this.mSmoking = mSmoking;
    }
}
