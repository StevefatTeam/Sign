package com.gisroad.sign.bean;

/**
 * Author: ngh
 * date: 2016/9/29
 */

public class Users {
    private String name;
    private String date_time;
    private String morning_to;
    private String morning_retreat;
    private String afternoon_to;
    private String afternoon_retreat;
    private String night_retreat;
    private String leave;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getMorning_to() {
        return morning_to;
    }

    public void setMorning_to(String morning_to) {
        this.morning_to = morning_to;
    }

    public String getMorning_retreat() {
        return morning_retreat;
    }

    public void setMorning_retreat(String morning_retreat) {
        this.morning_retreat = morning_retreat;
    }

    public String getAfternoon_to() {
        return afternoon_to;
    }

    public void setAfternoon_to(String afternoon_to) {
        this.afternoon_to = afternoon_to;
    }

    public String getAfternoon_retreat() {
        return afternoon_retreat;
    }

    public void setAfternoon_retreat(String afternoon_retreat) {
        this.afternoon_retreat = afternoon_retreat;
    }

    public String getNight_retreat() {
        return night_retreat;
    }

    public void setNight_retreat(String night_retreat) {
        this.night_retreat = night_retreat;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }
}
