package com.example.sahithi_rvs.studentsworld;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sahithi_rvs.studentsworld.activity.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Contactus extends Activity {

    TextView errorMsg;
    ProgressDialog prgDialog;
    // Email Edit View Object
    RequestParams params;

    SharedPreferences logged_in;
    SharedPreferences.Editor edit_login;
    String getStatus, cookies;
    String user_id = "";
    EditText review, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logged_in = getSharedPreferences("app", 0);
        edit_login = logged_in.edit();

        getStatus = logged_in.getString("login", "nil");
        String currentuser = logged_in.getString("uid", "nil");
        cookies = logged_in.getString("cookies", "nil");

        user_id = currentuser;
        Log.d("cookies", cookies);
        if (getStatus.equals("false")) {
            System.out.println("WrongPath");
            navigatetoLoginActivity();
            return;
        }
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False

        prgDialog.setCancelable(false);
        setContentView(R.layout.contactus);
        review = (EditText)findViewById(R.id.review);
        rating = (EditText)findViewById(R.id.rating);
    }


    public void sendreview(View view){
        String reviewtext = review.getText().toString();
        String ratingtext = rating.getText().toString();
        if(Utility.isNotNull(reviewtext) && Utility.isNotNull(ratingtext)) {
            try {
                params.put("review", reviewtext);
                params.put("rating", ratingtext);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(params);
            sendrequest(params);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    public void sendrequest(RequestParams params){
        try{
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://"+ LoginActivity.ip+":8081/useraccount/review", params, new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(String response){
                    Toast.makeText(getApplicationContext(), "Thanks for the Feedback", Toast.LENGTH_LONG).show();
                    navigatetoMaindupActivity();
                }

                @Override
                public void  onFailure(int statusCode, Throwable error, String content){
                    if(statusCode == 404){
                        Toast.makeText(getApplicationContext(), "Thanks for the Feedback", Toast.LENGTH_LONG).show();
                        navigatetoMaindupActivity();
                    }
                    else if(statusCode == 500) {
                        Toast.makeText(getApplicationContext(), "Thanks for the Feedback", Toast.LENGTH_LONG).show();
                        navigatetoMaindupActivity();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Thanks for the Feedback", Toast.LENGTH_LONG).show();
                        navigatetoMaindupActivity();
                    }
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Thanks for the Feedback", Toast.LENGTH_LONG).show();
            navigatetoMaindupActivity();
            e.printStackTrace();
        }
    }

    public void navigatetoMaindupActivity(){
        Intent maindupintent = new Intent(getApplicationContext(), MaindupActivity.class);
        maindupintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(maindupintent);
    }

    public void navigatetoLoginActivity() {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }
}

