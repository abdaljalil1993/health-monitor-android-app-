package com.VitalMonitor.vitalmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Req extends AppCompatActivity implements LocationListener{
    EditText e1,e2,e4;
    String url="http://192.168.1.15/sa2/mobile/patreq.php";
    Button btn1;
    LocationManager locationManager;
    Globalvar g;
    String lat,lan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req);
        e1=findViewById(R.id.un);
        e2=findViewById(R.id.pa);

        e4=findViewById(R.id.ph);

        g=(Globalvar)getApplication();
        btn1=findViewById(R.id.btn1);
        if(ContextCompat.checkSelfPermission(Req.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Req.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            } , 100);
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Toast.makeText(Req.this, "Geting your location now ", Toast.LENGTH_SHORT).show();
                try {
                    locationManager=(LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,Req.this);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //   Toast.makeText(this, ""+location.getLatitude()+", "+location.getLongitude(), Toast.LENGTH_SHORT).show();
        lan=String.valueOf(location.getLongitude());
        lat=String.valueOf(location.getLatitude());




        try {
            Geocoder geocoder=new Geocoder(Req.this, Locale.getDefault());
            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String adr=addresses.get(0).getAddressLine(0);
            Toast.makeText(this, " adr : "+adr, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }


    public void call(View v)
    {
        //  Toast.makeText(this, " dr phone  : "+g.getPhone(), Toast.LENGTH_SHORT).show();

        Intent i=new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:"+g.getPhone()));

        if(i.resolveActivity(getPackageManager())!=null)
        {
            startActivity(i);
        }
    }
    public void save(View v)
    {
        String heart=e1.getText().toString();
        String pre=e2.getText().toString();

        String temp=e4.getText().toString();

        StringRequest r=new StringRequest(Request.Method.POST, url,new Response.Listener<String>(){
            @Override
            public void onResponse(String o) {
                try {
                    JSONObject js=new JSONObject(o);
                    boolean res=js.getBoolean("f");
                    if(res==true) {



                        Toast.makeText(Req.this, "data send  successfully", Toast.LENGTH_SHORT).show();

                    }

                    else
                        Toast.makeText(Req.this, "data send  Failed", Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        } )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("heart",heart);
                params.put("presure", pre);

                params.put("temp", temp);
                params.put("lat", lat);
                params.put("lan", lan);
                params.put("pid", String.valueOf(g.getId()));

                return params;



            }
        }
                ;
        Volley.newRequestQueue(this).add(r);
    }
}
