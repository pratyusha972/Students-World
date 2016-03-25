package com.example.sahithi_rvs.studentsworld;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sahithi_rvs.studentsworld.activity.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sahithi_rvs.studentsworld.R;


public class OtpActivity extends Activity{
    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    // Otp Edit View Object
    EditText otpEt;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent();
        System.out.println(myIntent);
        username = myIntent.getStringExtra("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        errorMsg = (TextView)findViewById(R.id.login_error);
        // Find Name Edit View control by ID
        otpEt = (EditText)findViewById(R.id.otp);
        System.out.println("created");
    }

    public void verifyOtp(View view) {
        // Get otp ET control value
        String otp = otpEt.getText().toString();
        // Instantiate Http Request Param Object
        JSONObject params = new JSONObject();
        if (Utility.isNotNull(otp)) {
            // Put Http parameter name with value of Name Edit View control
            System.out.println("chekced not null");
            try {
                params.put("otp", otp);
                params.put("username", username);
            }catch (Exception e){
                e.printStackTrace();
            }
            sendOtp(params);
        }
        else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }
    }

    public void sendOtp(JSONObject params){
        // Show Progress Dialog
        prgDialog.show();
        try {
            StringEntity entity = new StringEntity(params.toString());
            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(getApplicationContext(), "http://10.0.2.2:8081/useraccount/otp/verify", entity, "application/json", new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {
                    // Hide Progress Dialog
                    prgDialog.hide();
                    try {
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if (obj.getBoolean("status")) {
                            setDefaultValues();
                            Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                            //navigatetoEditProfileActivity();
                            navigatetoLoginActivity();
                        }
                        // Else display error message
                        else {
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

    public void navigatetoLoginActivity(){
        Intent loginintent = new Intent(getApplicationContext(),LoginActivity.class);
        loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginintent);
    }

    public void setDefaultValues(){
        otpEt.setText("");
    }

}