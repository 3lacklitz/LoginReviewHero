package com.example.administrator.loginreviewhero.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hero implements Parcelable {
    private String image;
    private String title;
    private String intro;
    private String year;
    private String text;
    private String color;

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getIntro() {
        return intro;
    }

    public String getYear() {
        return year;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(intro);
        dest.writeString(year);
        dest.writeString(text);
        dest.writeString(color);
    }

    protected Hero(Parcel in) {
        image = in.readString();
        title = in.readString();
        intro = in.readString();
        year = in.readString();
        text = in.readString();
        color = in.readString();
    }

    public static final Creator<Hero> CREATOR = new Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel in) {
            return new Hero(in);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };
}
