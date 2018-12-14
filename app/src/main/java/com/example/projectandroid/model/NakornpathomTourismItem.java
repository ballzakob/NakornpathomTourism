package com.example.projectandroid.model;

import java.util.Locale;

public class NakornpathomTourismItem {

    public final long _id;
    public final String title;
    public final String location;
    public final String detail;
    public final String image; //final คือ ห้ามใครแก้แล้วเด้อ

    public NakornpathomTourismItem(long _id, String title, String location , String detail, String image) {
        this._id = _id;
        this.title = title;
        this.location = location;
        this.image = image;
        this.detail = detail;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "ชื่อ %s\nเบอร์โทร: %s",
                this.title,
                this.location);
    }
}