package com.VitalMonitor.vitalmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText e1,e2,e4,e5;
    String url="http://192.168.1.15/sa2/mobile/signup.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        e1=findViewById(R.id.un);
        e2=findViewById(R.id.pa);

        e4=findViewById(R.id.ph);
        e5=findViewById(R.id.adr);
    }

    public void login(View v)
    {
        String uname=e1.getText().toString();
        String pass=e2.getText().toString();

        String phone=e4.getText().toString();
        String address=e5.getText().toString();
        StringRequest r=new StringRequest(Request.Method.POST, url,new Response.Listener<String>(){
            @Override
            public void onResponse(String o) {
                try {
                    JSONObject js=new JSONObject(o);
                    boolean res=js.getBoolean("f");
                    if(res==true) {



                        Toast.makeText(Signup.this, "create account successfully", Toast.LENGTH_SHORT).show();

                    }

                    else
                        Toast.makeText(Signup.this, "create account Failed", Toast.LENGTH_SHORT).show();


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
                params.put("username",uname);
                params.put("password", pass);

                params.put("section", phone);
                params.put("address", address);
                return params;



            }
        }
                ;
        Volley.newRequestQueue(this).add(r);
        //  RequestQueue q = new RequestQueue(Volley.newRequestQueue(Volley.newRequestQueue(Signup.this, null)));
        //  q.add(r);
    }
}