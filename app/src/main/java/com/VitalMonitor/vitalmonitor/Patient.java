package com.VitalMonitor.vitalmonitor;

public class Patient {

    String name,phone,address,state2;
int id,age;
    public Patient(String name, String phone, int age, String address, int id,String state2) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.address = address;
        this.id = id;
        this.state2=state2;
    }
}
