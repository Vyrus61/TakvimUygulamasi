package com.example.firerectakvim;

public class Note {
    private String title;
    private String description;
    private int priority;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private String repeatType;
    private int dayE;
    private int monthE;
    private int yearE;

    public int getDayE() {
        return dayE;
    }

    public void setDayE(int dayE) {
        this.dayE = dayE;
    }

    public int getMonthE() {
        return monthE;
    }

    public void setMonthE(int monthE) {
        this.monthE = monthE;
    }

    public int getYearE() {
        return yearE;
    }

    public void setYearE(int yearE) {
        this.yearE = yearE;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public Note() {
        //empty constructor needed
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Note(String title, String description, int priority, int day, int month, int year, int hour, int minute,String repeatType,int dayE,int monthE,int yearE) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.repeatType=repeatType;
        this.dayE=dayE;
        this.monthE=monthE;
        this.yearE=yearE;
    }
}
