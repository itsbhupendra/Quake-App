package com.example.quake;

public class Earthquake {

    private double mMagnitude;

    private String mLocation;

    private  String mUrl;

//    private String mDate;

    private long mtimeInMilliseconds;
//    public Earthquake(String s, String san_francisco, String s1) {
//        mMagnitude=mag;
//        mLocation=loc;
//        mDate=date;
//    }

    public  Earthquake(double mag,String loc, long date,String url){

        mMagnitude=mag;
        mLocation=loc;
        mtimeInMilliseconds=date;
        mUrl=url;
    }

    public double getmMagnitude(){
        return mMagnitude;
    }

    public String getmLocation(){
        return mLocation;
    }

    public long getmtimeInMilliseconds(){
        return mtimeInMilliseconds;
    }

    public String getmUrl() {
        return mUrl;
    }
}
