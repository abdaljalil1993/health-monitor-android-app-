package com.VitalMonitor.vitalmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
TextView tt,pphone,paddress,age1,heart,state,temp,presure;
    String url="http://192.168.1.15/sa2/mobile/patstate.php",lan,lat,st,temp1,presur1;
    int h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tt=findViewById(R.id.pname);
        pphone=findViewById(R.id.pphone);
        paddress=findViewById(R.id.paddress);
        age1=findViewById(R.id.age);
        heart=findViewById(R.id.heart);
        temp=findViewById(R.id.temp);
        presure=findViewById(R.id.presure);
        state=findViewById(R.id.state);
        Intent i=getIntent();
        int  id=i.getExtras().getInt("id");


        String name=i.getExtras().getString("name");
        String phone=i.getExtras().getString("phone");
        String address=i.getExtras().getString("address");
        int  age=i.getExtras().getInt("age");
        tt.setText("patient name :"+name);
        pphone.setText("patient phone  :"+phone);
        paddress.setText("patient address :"+address);
        age1.setText("patient Age  :"+String.valueOf(age));



        StringRequest r=new StringRequest(Request.Method.POST, url,new Response.Listener<String>(){
            @Override
            public void onResponse(String o) {
                try {
                  JSONObject js=new JSONObject(o);
                 h=js.getInt("heart");
                     st=js.getString("state");
                     lan=js.getString("lan");
                     lat=js.getString("lat");
                    temp1=js.getString("temp");
                    presur1=js.getString("presur");
                   // Toast.makeText(getApplicationContext(), ""+st+" "+lat, Toast.LENGTH_SHORT).show();
                     heart.setText("patient heart beats :"+String.valueOf(h));
                    state.setText("patient State : "+st);
                    temp.setText("Temperture : "+temp1);
                    presure.setText("Presure : "+presur1);

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
                params.put("id",String.valueOf(id));


                return params;



            }
        }
                ;
        Volley.newRequestQueue(this).add(r);



    }


    public void loc(View v)
    {

        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("geo:"+lan+"?z=21"));

        if(i.resolveActivity(getPackageManager())!=null)
        {
            startActivity(i);
        }

    }
}