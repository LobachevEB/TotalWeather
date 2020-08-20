package com.example.totalweather.common;

import android.text.Editable;

public class Singleton {
    String Temperature;
    String Location;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
    private static final Singleton ourInstance = new Singleton();
    public static Singleton getInstance() {
        return ourInstance;
    }
    private Singleton() { }
    public void setTemperature(String editValue) {
        this.Temperature = editValue;
    }
    public String getTemperature() {
        return Temperature;
    }
}
