package com.ykatakuri.footx.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Events implements Parcelable {
    private String author, field, phone, date, time, format, id;

    public Events() {
    }

    public Events(String author, String field, String phone, String date, String time, String format, String id) {
        this.author = author;
        this.field = field;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.format = format;
        this.id = id;
    }

    protected Events(Parcel in) {
        author = in.readString();
        field = in.readString();
        phone = in.readString();
        date = in.readString();
        time = in.readString();
        format = in.readString();
        id = in.readString();
    }

    public static final Creator<Events> CREATOR = new Creator<Events>() {
        @Override
        public Events createFromParcel(Parcel in) {
            return new Events(in);
        }

        @Override
        public Events[] newArray(int size) {
            return new Events[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(field);
        dest.writeString(phone);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(format);
        dest.writeString(id);
    }
}
