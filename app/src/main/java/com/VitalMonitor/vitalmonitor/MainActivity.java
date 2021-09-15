package com.VitalMonitor.vitalmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v)
    {
        startActivity(new Intent(MainActivity.this,Login.class));
    }


    public void signup(View v)
    {
        startActivity(new Intent(MainActivity.this,Signup.class));
    }

    public void pat1(View v)
    {
        startActivity(new Intent(MainActivity.this,Patientlogin.class));
    }
}