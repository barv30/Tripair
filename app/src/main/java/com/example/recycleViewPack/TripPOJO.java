package com.example.recycleViewPack;

import com.example.dataUser.Trip;

public class TripPOJO
{
    private String mCountry;
    private String mCity;
    private int mADay;
    private int  mAMonth;
    private int mAYear;
    private int mLDay;
    private int mLMonth;
    private int mLYear;
    private String mFullDate;

   public TripPOJO(){}
        public TripPOJO(String iCountry, String iCity,int iADay,int iAMonth, int iAYear,int iLDay ,int iLMonth,int iLYear)
    {
        this.mCountry = iCountry;
        this.mCity = iCity;
        this.mADay = iADay;
        this.mAMonth = iAMonth;
        this.mAYear = iAYear;
        this.mLDay = iLDay;
        this.mLMonth=iLMonth;
        this.mAYear=iLYear;
        if(mLDay == 0 && mLMonth == 0 && mAYear == 0)
        {
            mFullDate = mADay+"."+mAMonth+"."+mAYear+"(One way)";
        }
        else
        {
            mFullDate=mADay+"."+mAMonth+"."+mAYear+"-"+mLDay+"."+mLMonth+"."+mLYear;
        }

    }

    public int getmADay() {
        return mADay;
    }

    public int getmAMonth() {
        return mAMonth;
    }

    public int getmAYear() {
        return mAYear;
    }

    public int getmLDay() {
        return mLDay;
    }

    public int getmLMonth() {
        return mLMonth;
    }

    public int getmLYear() {
        return mLYear;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmCountry() {
        return mCountry;
    }



    public String getmFullDate() {
        return mFullDate;
    }


    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public void setmADay(int mADay) {
        this.mADay = mADay;
    }

    public void setmAMonth(int mAMonth) {
        this.mAMonth = mAMonth;
    }

    public void setmAYear(int mAYear) {
        this.mAYear = mAYear;
    }

    public void setmLDay(int mLDay) {
        this.mLDay = mLDay;
    }

    public void setmLMonth(int mLMonth) {
        this.mLMonth = mLMonth;
    }

    public void setmLYear(int mLYear) {
        this.mLYear = mLYear;
    }


    public void setmFullDate(String mFullDate) {
        this.mFullDate = mFullDate;
    }


}
