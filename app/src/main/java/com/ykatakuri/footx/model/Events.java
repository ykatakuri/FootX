package com.ykatakuri.footx.model;

public class Events {
    String field, phone, date, time, format;

    public Events() {
    }

    public Events(String field, String phone, String date, String time, String format) {
        this.field = field;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.format = format;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}