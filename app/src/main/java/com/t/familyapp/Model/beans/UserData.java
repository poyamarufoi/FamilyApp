package com.t.familyapp.Model.beans;

import java.io.Serializable;

/**
 * User情報を格納するクラス
 *
 */
public class UserData implements Serializable {
    private String name;
    private String morningTimeStart;
    private String morningTimeEnd;
    private String nightTime;
    private boolean breakfastCheck;
    private boolean dinnerCheck;
    private String firebaseKey;

    public UserData() {
    }

    public UserData(String firebaseKey,String name){
        this.firebaseKey = firebaseKey;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMorningTimeStart() {
        return morningTimeStart;
    }

    public String getMorningTimeEnd() {
        return morningTimeEnd;
    }

    public String getNightTime() {
        return nightTime;
    }

    public boolean getBreakfastCheck() {
        return breakfastCheck;
    }

    public  boolean getDinnerCheck() {
        return dinnerCheck;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMorningTimeStart(String morningTimeStart) {
        this.morningTimeStart = morningTimeStart;
    }

    public void setMorningTimeEnd(String morningTimeEnd) {
        this.morningTimeEnd = morningTimeEnd;
    }

    public void setNightTime(String nightTime) {
        this.nightTime = nightTime;
    }

    public void setBreakfastCheck(boolean breakfastCheck) {
        this.breakfastCheck = breakfastCheck;
    }

    public void setDinnerCheck(boolean dinnerCheck) {
        this.dinnerCheck = dinnerCheck;
    }
}
