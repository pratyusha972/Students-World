package com.example.sahithi_rvs.studentsworld;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.util.*;
import android.widget.Toast;

import com.example.sahithi_rvs.studentsworld.activity.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sahithi_rvs.studentsworld.MyProfile;
public class EditProfile extends Activity {

    // Progress Dialog Object
    //ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    // First Name Edit View Object
    EditText firstnameET;
    // Last Name Edit View Object
    EditText lastnameET;
    // Start Degree Edit View Object
    EditText startdegreeET;
    // End Degree Edit View Object
    EditText enddegreeET;
    // Address Edit View Object
    EditText addressline1ET;
    // Address2 Edit View Object
    EditText addressline2ET;
    // College Edit View Object
    EditText collegeET;
    // DOB Edit View Object
    EditText dobET;
    // Mobile Edit View Object
    EditText mobileEt;
    // Degree Edit View Object
    EditText degreeET;
    MyProfile profile;
    String getStatus="",currentuser="",cookies="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences logged_in;

        setContentView(R.layout.updateprofile);
        // Find Error Msg Text View control by ID
        errorMsg = (TextView)findViewById(R.id.register_error);
        // Find Name Edit View control by ID
        firstnameET = (EditText)findViewById(R.id.first_name);
        // Find Email Edit View control by ID
        lastnameET = (EditText)findViewById(R.id.last_name);
        // Find Password Edit View control by ID
        startdegreeET = (EditText)findViewById(R.id.start_degree);
        // Instantiate Progress Dialog object
        enddegreeET = (EditText)findViewById(R.id.end_degree);
        // Instantiate Progress Dialog object
        addressline1ET = (EditText)findViewById(R.id.address_line1);
        // Instantiate Progress Dialog object
        addressline2ET = (EditText)findViewById(R.id.address_line2);
        // Instantiate Progress Dialog object
        dobET = (EditText)findViewById(R.id.dob);
        // Instantiate Progress Dialog object
        collegeET = (EditText)findViewById(R.id.college_name);
        // Find Mobile edit View Control by ID
        mobileEt = (EditText)findViewById(R.id.mobile_no);
        // Find Degree edit View Control by ID
        degreeET = (EditText)findViewById(R.id.degree);
        // Instantiate Progress Dialog object
        //  prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        // prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        //  prgDialog.setCancelable(false);
        logged_in= getSharedPreferences("app", Context.MODE_PRIVATE);
        getStatus=logged_in.getString("login", "nil");
        currentuser=logged_in.getString("uid","nil");
        cookies=logged_in.getString("cookies","nil");
        Log.d("uid", currentuser);
        Log.d("status", getStatus);
        RequestParams params = new RequestParams();
        params.put("user_id", currentuser);
      //  profile= new MyProfile();
        setDefaultValues(params);
    }


    public void editUser(View view){

        // Get NAme ET control value
        String firstname = firstnameET.getText().toString();
        // Get Email ET control value
        String lastname = lastnameET.getText().toString();
        // Get Password ET control value
        String startdeg = startdegreeET.getText().toString();
        // Get Password ET control value
        String enddeg = enddegreeET.getText().toString();
        // Get Password ET control value
        String dob = dobET.getText().toString();
        // Get Password ET control value
        String college = collegeET.getText().toString();
        // Get Password ET control value
        String addressline1 = addressline1ET.getText().toString();
        // Get Password ET control value
        String addressline2 = addressline2ET.getText().toString();
        // Get Mobile Number
        String mobile_no = mobileEt.getText().toString();
        // Get Degree
        String degree = degreeET.getText().toString();
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
       // if(Utility.isNotNull(firstname) && Utility.isNotNull(lastname) && Utility.isNotNull(degree) && Utility.isNotNull(addressline1) && Utility.isNotNull(startdeg) && Utility.isNotNull(enddeg)){
            // When Email entered is Valid
            // Put Http parameter first name with value of First Name Edit View Control
            params.put("firstname","\""+firstname+"\"");
            Log.d("a", firstname);
            // Put Http parameter last name with value of Last Name Edit View Control
            params.put("lastname","\""+lastname+"\"");
            Log.d("a", firstname); // Put Http parameter degree with value of Degree Edit View Control
            params.put("degree","\""+ degree+"\"");
            Log.d("a", firstname); // Put Http parameter degree with value of Degree Edit View Control
            // Put Http parameter start degree with value of Start Degree Edit View Control
            params.put("start_degree","\""+startdeg+"\"");
            Log.d("a", firstname); // Put Http parameter degree with value of Degree Edit View Control
            // Put Http parameter end degree with value of End Degree Edit View Control
            params.put("end_degree","\""+enddeg+"\"");
            Log.d("a", firstname); // Put Http parameter degree with value of Degree Edit View Control
            // Put Http parameter dob with value of Last DOB View Control
            params.put("dob","\""+dob+"\"");
            Log.d("a","\""+ firstname); // Put Http parameter degree with value of Degree Edit View Control
            String address=addressline1 + "\n"+addressline2;
            // Put Http parameter address line1 with value of Address Line1 Edit View Control
            Log.d("a", firstname); // Put Http parameter degree with value of Degree Edit View Control
            Log.d("a", firstname); // Put Http parameter degree with value of Degree Edit View Control
           params.put("address", "\"" + address + "\"");
            // Put Http parameter Mobile with value of Mobile Edit View Control
            Log.d("a", firstname); // Put Http parameter degree with value of Degree Edit View Control
            params.put("college_name", "\""+college+"\"");

            Log.d("a", firstname); // Put Http parameter degree with value of Degree Edit View Control
            params.put("mobile", "\""+mobile_no+"\"");
            params.put("id","70");
            params.put("cookies",cookies);
            Log.d("params", params.toString());
            // Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            invokeWS(params);
       /* }

        // When any of the Edit View control left blank
        else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }*/

    }
    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        //  prgDialog.show();
        try {
            StringEntity entity = new StringEntity(params.toString());
            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            client.get( "http://" + LoginActivity.ip + ":8081/useraccount/profile/editprofile", params, new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {
                    // Hide Progress Dialog
                    //     prgDialog.hide();
                    try {
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        Log.d("success","sdf");
                        Log.d("success",obj.toString());
                        // When the JSON response has status boolean value assigned with true
                        if (obj.getBoolean("status")) {

                            //if (true){
                            // Set Default Values for Edit View controls

                            // Display successfully registered message using Toast
                            //  String getStatus=pref.getString("register", "nil");
                       /* if(getStatus.equals("true")){

                        }
                        else {*/
                            Toast.makeText(getApplicationContext(), "You are successfully edited!", Toast.LENGTH_LONG).show();
                            navigatetoMaindupActivity();
                        }
                        // Else display error message
                        else {

//                            errorMsg.setText(obj.getString("error_msg"));
                            Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                            navigatetoLoginActivity();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //navigatetoOtpActivity();
                        Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();


                    }
                }

                // When the response returned by REST has Http response code other than '200'
                @Override
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    Log.d("status", Integer.toString(statusCode));
                    // Hide Progress Dialog
                    //     prgDialog.hide();
                    // When Http response code is '404'
                    if (statusCode == 404) {
                        Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code is '500'
                    else if (statusCode == 500) {
                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code other than 404, 500
                    else {
                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e){

        }
    }

    public void setDefaultValues(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + LoginActivity.ip + ":8081/useraccount/profile/viewprofile", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.d("status","success");
                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("status")) {
                        setDefault(obj.getJSONArray("jsonarray"));
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

    public void setDefault(JSONArray jsarray){

        try {
            JSONObject obj,jobj;
            Log.d("set","def");
            obj = jsarray.getJSONObject(0);
            Log.d("json", obj.toString());
            firstnameET.setText(obj.getString("firstname"));
            Log.d("first", firstnameET.toString());
            lastnameET.setText(obj.getString("lastname"));
            collegeET.setText(obj.getString("college_name"));
            mobileEt.setText(obj.getString("mobile"));
            degreeET.setText(obj.getString("degree"));
            startdegreeET.setText(obj.getString("start_degree"));
            Log.d("4", firstnameET.toString());
            enddegreeET.setText(obj.getString("end_degree"));
            Log.d("5", firstnameET.toString());
            dobET.setText(obj.getString("dob"));
            Log.d("6", firstnameET.toString());
            String add = obj.getString("address");
            Log.d("address",add);
            String lines[] = add.split("\\r?\\n");
            addressline1ET.setText(lines[0]);
            addressline2ET.setText(lines[1]);

        }catch (Exception e){

        }
    }

    public void navigatetoMaindupActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),MaindupActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    public void navigatetoLoginActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }



}