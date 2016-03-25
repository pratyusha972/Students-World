package com.example.sahithi_rvs.studentsworld;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyProfile extends Activity {
    SharedPreferences logged_in;
    String getStatus="",currentuser="";
    String cookies;
    TextView firstname;
    // Last Name Edit View Object
    TextView lastname;
    // Start Degree Edit View Object
    TextView startdegree;
    // End Degree Edit View Object
    TextView enddegree;
    // Address Edit View Object
    TextView addressline1;
    // Address2 Edit View Object
    TextView addressline2;
    // College Edit View Object
    TextView college;
    // DOB Edit View Object
    TextView dob;
    // Mobile Edit View Object
    TextView mobile;
    // Degree Edit View Object
    TextView degree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        firstname = (TextView)findViewById(R.id.first_name);
        lastname = (TextView)findViewById(R.id.last_name);
        startdegree = (TextView)findViewById(R.id.start_degree);
        enddegree = (TextView)findViewById(R.id.end_degree);
        addressline1 = (TextView)findViewById(R.id.address_line1);
        addressline2 = (TextView)findViewById(R.id.address_line2);
        dob = (TextView)findViewById(R.id.dob);
        college = (TextView)findViewById(R.id.college_name);
        mobile = (TextView)findViewById(R.id.mobile_no);
        degree = (TextView)findViewById(R.id.degree);

        logged_in= getSharedPreferences("app", Context.MODE_PRIVATE);
        getStatus=logged_in.getString("login", "nil");
        currentuser=logged_in.getString("uid","nil");

        Log.d("uid",currentuser);
        Log.d("status",getStatus);

        RequestParams params = new RequestParams();
        params.put("user_id",currentuser);
        invokeWS(params);
    }
    public void invokeWS(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + LoginActivity.ip + ":8081/useraccount/profile/viewprofile", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.d("status","success");
                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("status")) {
                        setDefaultValues(obj.getJSONArray("jsonarray"));
//                        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                    } else {
//                        Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    //                   Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                Log.d("done","sddsf");
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                Log.d("status","failure");
                if (statusCode == 404) {
//                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
//                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    //                   Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setDefaultValues(JSONArray jsarray){

        try {
            JSONObject obj,jobj;
            Log.d("set", "def");
            obj = jsarray.getJSONObject(0);
            Log.d("json", obj.toString());
            firstname.setText(obj.getString("firstname"));
            firstname.setBackgroundColor(Color.rgb(224, 255, 255));
            firstname.setPadding(50, 50, 50, 50);
            firstname.setTextSize(15);

            Log.d("first", firstname.toString());
            lastname.setText(obj.getString("lastname"));
            lastname.setBackgroundColor(Color.rgb(224, 255, 255));
            lastname.setPadding(50, 50, 50, 50);
            lastname.setTextSize(15);

            Log.d("1", mobile.toString());
            college.setText(obj.getString("college_name"));
            college.setBackgroundColor(Color.rgb(224, 255, 255));
            college.setPadding(50, 50, 50, 50);
            college.setTextSize(15);

            mobile.setText(obj.getString("mobile"));
            mobile.setBackgroundColor(Color.rgb(224, 255, 255));
            mobile.setPadding(50, 50, 50, 50);
            mobile.setTextSize(15);

            Log.d("2", firstname.toString());
            degree.setText(obj.getString("degree"));
            degree.setBackgroundColor(Color.rgb(224, 255, 255));
            degree.setPadding(50, 50, 50, 50);
            degree.setTextSize(15);

            Log.d("3", firstname.toString());
            startdegree.setText(obj.getString("start_degree"));
            startdegree.setBackgroundColor(Color.rgb(224, 255, 255));
            startdegree.setPadding(50, 50, 50, 50);
            startdegree.setTextSize(15);


            Log.d("4", firstname.toString());
            enddegree.setText(obj.getString("end_degree"));
            enddegree.setBackgroundColor(Color.rgb(224, 255, 255));
            enddegree.setPadding(50, 50, 50, 50);
            enddegree.setTextSize(15);

            Log.d("5", firstname.toString());
            dob.setText(obj.getString("dob"));
            dob.setBackgroundColor(Color.rgb(224, 255, 255));
            dob.setPadding(50, 50, 50, 50);
            dob.setTextSize(15);

            Log.d("6", firstname.toString());
            String add = obj.getString("address");
            Log.d("address", add);
            String lines[] = add.split("\\r?\\n");
            addressline1.setText(lines[0]);
            addressline1.setBackgroundColor(Color.rgb(224, 255, 255));
            addressline1.setPadding(50, 50, 50, 50);
            addressline1.setTextSize(15);

            addressline2.setText(lines[1]);
            addressline2.setBackgroundColor(Color.rgb(224, 255, 255));
            addressline2.setPadding(50, 50, 50, 50);
            addressline2.setTextSize(15);


        }catch (Exception e){

        }
    }
}

