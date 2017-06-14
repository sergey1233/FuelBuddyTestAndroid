package com.finapp.fuelbuddytest.ui;


import android.os.Parcel;
import android.os.Parcelable;

public class Petrole implements Parcelable {

    private String title = "";
    private String cost = "0 ₽";
    private String time = "";
    private String icon = "";
    private String adress = "";
    private double distance = 0;
    private double latitude = 0;
    private double longitude = 0;


    public Petrole(String title, String cost, String time, String icon, String adress, double distance, double lat, double lon) {
        this.title = title;
        this.cost = cost;
        this.time = time;
        this.icon = icon;
        this.adress = adress;
        this.distance = distance;
        this.latitude = lat;
        this.longitude = lon;
    }

    public Petrole(Parcel source) {
        this.title = source.readString();
        this.cost = source.readString();
        this.time = source.readString();
        this.icon = source.readString();
        this.adress = source.readString();
        this.distance = source.readDouble();
        this.latitude = source.readDouble();
        this.longitude = source.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(cost);
        parcel.writeString(time);
        parcel.writeString(icon);
        parcel.writeString(adress);
        parcel.writeDouble(distance);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<Petrole> CREATOR = new Creator<Petrole>() {

        @Override
        public Petrole createFromParcel(Parcel source) {
            return new Petrole(source);
        }

        @Override
        public Petrole[] newArray(int size) {
            return new Petrole[size];
        }
    };

    public String getTitle() {
        return this.title;
    }

    public String getCost() {
        return this.cost;
    }

    public String getTime() {
        return this.time;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getAdress() {
        return this.adress;
    }

    public double getDistance() {
        return this.distance;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongtitude() {
        return this.longitude;
    }
}
