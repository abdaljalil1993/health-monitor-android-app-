package com.VitalMonitor.vitalmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Viewpatient extends AppCompatActivity {
ListView mylist;
ArrayList<Patient> listwithdata=new ArrayList<Patient>();
public static String state="";
int id;

    String url="http://192.168.1.15/sa2/mobile/allpatient.php";
    String url2="http://192.168.1.15/sa2/mobile/getstate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpatient);

        mylist=findViewById(R.id.mylist);
        //start
        StringRequest r=new StringRequest(Request.Method.GET, url,new Response.Listener<String>(){
            @Override
            public void onResponse(String o) {


                try {
                    JSONObject js=new JSONObject(o);
                    JSONArray jr=js.getJSONArray("all");
                   for(int i=0;i<jr.length();i++)
                   {
                       JSONObject j=jr.getJSONObject(i);
                      id=j.getInt("id");
                       String name=j.getString("name");
                       String phone=j.getString("phone");
                       String address=j.getString("address");
                       int age=j.getInt("age");



                       //get the state
                       StringRequest r1=new StringRequest(Request.Method.POST, url2,new Response.Listener<String>(){
                           @Override
                           public void onResponse(String o1) {
                               try {
                                   JSONObject js1=new JSONObject(o1);

                                String   state1=js1.getString("ss");

                                state=state1;

                               } catch (JSONException e1) {
                                   e1.printStackTrace();
                               }

                           }
                       },new Response.ErrorListener(){
                           @Override
                           public void onErrorResponse(VolleyError volleyError1) {

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

                       Volley.newRequestQueue(getApplicationContext()).add(r1);


                       listwithdata.add(new Patient(name,phone,age,address,id,state));
     //              Toast.makeText(Viewpatient.this,  " "+listwithdata.get(i).state2.toString(), Toast.LENGTH_SHORT).show();


                       //end get state













                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myadapter m=new myadapter(listwithdata);
                mylist.setAdapter(m);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        } ){

        };

        Volley.newRequestQueue(this).add(r);

        myadapter m=new myadapter(listwithdata);
        mylist.setAdapter(m);
        //end
         Toast.makeText(Viewpatient.this, " ops", Toast.LENGTH_SHORT).show();

    }


    class myadapter extends BaseAdapter{
        ArrayList<Patient> ls;

        public myadapter(ArrayList<Patient> ls) {
            this.ls = ls;
        }

        @Override
        public int getCount() {
            return ls.size();
        }

        @Override
        public Object getItem(int position) {
            return ls.get(position).id;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View v;
          LayoutInflater inf = getLayoutInflater();
          v=inf.inflate(R.layout.patientitem,null);

            TextView t1=v.findViewById(R.id.n);
            TextView t2=v.findViewById(R.id.adr);

            TextView t3=v.findViewById(R.id.sss);

        //    Toast.makeText(Viewpatient.this, "Patient "+ls.get(position).name+" "+ls.get(position).state2, Toast.LENGTH_SHORT).show();

            t1.setText(ls.get(position).name.toString());

            t2.setText(ls.get(position).address.toString());
          //Toast.makeText(Viewpatient.this,  " "+ls.get(position).state2.toString(), Toast.LENGTH_SHORT).show();


            if(ls.get(position).state2.toString()=="risk") {
                t3.setText("risk");
                t3.setBackgroundColor(getResources().getColor(R.color.red));
            }

            else{
                t3.setText("good");

                t3.setBackgroundColor(getResources().getColor(R.color.green));
            }




            t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(Viewpatient.this,Details.class);
                    i.putExtra("id",ls.get(position).id);
                    i.putExtra("name",ls.get(position).name);
                    i.putExtra("phone",ls.get(position).phone);
                    i.putExtra("age",ls.get(position).age);
                    i.putExtra("address",ls.get(position).address);

                    startActivity(i);


                }
            });



            t2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(Viewpatient.this,Details.class);
                    i.putExtra("id",ls.get(position).id);
                    i.putExtra("name",ls.get(position).name);
                    i.putExtra("phone",ls.get(position).phone);
                    i.putExtra("age",ls.get(position).age);
                    i.putExtra("address",ls.get(position).address);

                    startActivity(i);


                }
            });






            return v;
        }
    }
}