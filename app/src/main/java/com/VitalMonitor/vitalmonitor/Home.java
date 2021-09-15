package com.VitalMonitor.vitalmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void add(View v)
    {
        startActivity(new Intent(Home.this,Addpatient.class));
    }
    public void dem(View v)
    {
        startActivity(new Intent(Home.this,Demondcar.class));
    }


    public void see(View v)
    {
        startActivity(new Intent(Home.this,Viewpatient.class));
    }
}