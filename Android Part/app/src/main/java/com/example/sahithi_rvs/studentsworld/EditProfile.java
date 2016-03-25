package com.example.sahithi_rvs.studentsworld;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends Activity {

    // Progress Dialog Object
    ProgressDialog prgDialog;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
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
       if(Utility.isNotNull(firstname) && Utility.isNotNull(lastname) && Utility.isNotNull(degree) && Utility.isNotNull(addressline1) && Utility.isNotNull(startdeg) && Utility.isNotNull(enddeg)){
            // When Email entered is Valid
           // Put Http parameter first name with value of First Name Edit View Control
           params.put("first_name",firstname);
           // Put Http parameter last name with value of Last Name Edit View Control
           params.put("last_name",lastname);
           // Put Http parameter degree with value of Degree Edit View Control
           params.put("degree",degree);
           // Put Http parameter start degree with value of Start Degree Edit View Control
           params.put("start_degree",startdeg);
           // Put Http parameter end degree with value of End Degree Edit View Control
           params.put("end_degree",enddeg);
           // Put Http parameter dob with value of Last DOB View Control
           params.put("dob",dob);
           // Put Http parameter address line1 with value of Address Line1 Edit View Control
           params.put("address_line1",addressline1);
           // Put Http parameter address line2 with value of Address Line2 Edit View Control
           params.put("address_line2",addressline2);
           // Put Http parameter Mobile with value of Mobile Edit View Control
           params.put("college",college);

           params.put("mobile_no", mobile_no);
           Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
               // invokeWS(params);
            }

        // When any of the Edit View control left blank
       else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }
    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2:8081/useraccount/register/doregister",params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);

                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){

                        //if (true){
                        // Set Default Values for Edit View controls
                        setDefaultValues();
                        // Display successfully registered message using Toast
                      //  String getStatus=pref.getString("register", "nil");
                       /* if(getStatus.equals("true")){

                        }
                        else {*/
                        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                        navigatetoMaindupActivity();
                    }
                    // Else display error message
                    else{
                        errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
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
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setDefaultValues() {
        firstnameET.setText("");
        lastnameET.setText("");
        startdegreeET.setText("");
        enddegreeET.setText("");
        mobileEt.setText("");
        degreeET.setText("");
        collegeET.setText("");
        addressline1ET.setText("");
        addressline1ET.setText("");
        dobET.setText("");
    }

    public void navigatetoMaindupActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),MaindupActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

}
