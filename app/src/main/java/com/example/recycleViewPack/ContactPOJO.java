package com.example.recycleViewPack;

public class ContactPOJO {

    private String mName;
    private String mDateDest;
    private String mSmoking;
    private int mAge;

    public ContactPOJO() {}
    public ContactPOJO(String name, int day,int month,int year,boolean isSmoking,int age) {
        mName = name;
        mDateDest= day+"."+month+"."+year;
        if(isSmoking)
        {
            mSmoking = "Yes";
        }
        else
        {
            mSmoking="No";
        }
        mAge=age;

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


    public void setmName(String mName) {
        this.mName = mName;
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
