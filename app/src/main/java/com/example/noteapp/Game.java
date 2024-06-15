package com.example.noteapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
    private String title;
    private String imageUrl;

    public Game(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    protected Game(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(imageUrl);
    }
}
