package com.example.sahithi_rvs.studentsworld;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionActivity extends Activity {

    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    // Email Edit View Object

    String test_id;
    SharedPreferences logged_in;
    SharedPreferences.Editor edit_login;
    String getStatus,cookies;
    int currentuser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();
        test_id = myIntent.getStringExtra("test_id");

        getStatus=logged_in.getString("login", "nil");
        if (getStatus == "false"){
            navigatetoLoginActivity();
            return;
        }
        currentuser=logged_in.getInt("uid",0);
        cookies=logged_in.getString("cookies","nil");
        String user_id = Integer.toString(currentuser);
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        Log.d("cookies",cookies);
        Log.d("test",test_id);
        Log.d("currentuser",user_id);
        prgDialog.setCancelable(false);
        setContentView(R.layout.question);
        RequestParams params = new RequestParams();
        params.put("test_id", test_id);
        params.put("user_id", user_id);
        params.put("cookies", cookies);
        invokeWS(params);
    }

    public void invokeWS(RequestParams params){
        prgDialog.show();
        try {
            StringEntity entity = new StringEntity(params.toString());
            System.out.println(entity);
            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(getApplicationContext(),"http://10.0.2.2:8081/useraccount/test/getquestion",entity,"application/json" ,new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {
                    // Hide Progress Dialog
                    System.out.println("qwerty");
                    prgDialog.hide();
                    try {
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if(obj.getBoolean("status")){
                            System.out.println("printing obj");
                            System.out.println(obj);
                        }
                        else{
                            errorMsg.setText(obj.getString("error_msg"));
                            String errortag =  obj.getString("error_msg");
                            if( errortag == "unverified"){
                                navigatetoLoginActivity();
                            }
                            Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }
                }

                // When the response returned by REST has Http response code other than '200'
                @Override
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    Log.d("sdfsdf",Integer.toString(statusCode));
                    System.out.println("qwertydgdfg");
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
        }catch (Exception e){

        }
    }

    public void navigatetoLoginActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }
}
