package com.VitalMonitor.vitalmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Patientlogin extends AppCompatActivity {
    EditText e1,e2;
    String url="http://192.168.1.15/sa2/mobile/loginp.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlogin);

        e1=findViewById(R.id.un);
        e2=findViewById(R.id.pa);
    }


    public void login(View v)
    {
        String uname=e1.getText().toString();
        String pass=e2.getText().toString();
        StringRequest r=new StringRequest(Request.Method.POST, url,new Response.Listener<String>(){
            @Override
            public void onResponse(String o) {
                try {
                    JSONObject js=new JSONObject(o);
                    boolean res=js.getBoolean("f");
                    if(res==true) {
                        Toast.makeText(Patientlogin.this, "Welcome..", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Patientlogin.this,Req.class));


                    }

                    else
                        Toast.makeText(Patientlogin.this, "Login  Failed please try agin later", Toast.LENGTH_SHORT).show();


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

                return params;



            }
        }
                ;
        Volley.newRequestQueue(this).add(r);

        int x=2;// retry count
        r.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}