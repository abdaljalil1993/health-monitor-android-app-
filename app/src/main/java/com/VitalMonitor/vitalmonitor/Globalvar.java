package com.VitalMonitor.vitalmonitor;

import android.app.Application;

public class Globalvar extends Application {
    int id;
    String phone;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
