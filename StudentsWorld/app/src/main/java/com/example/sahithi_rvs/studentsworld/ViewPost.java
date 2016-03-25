package com.example.sahithi_rvs.studentsworld;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pratyusha on 22/11/15.
 */
public class ViewPost extends Activity {

    String postid;

    //  ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    String description;
    String tags;
    String emailid;
    String title;
    TextView descriptionview;
    TextView emailidview;
    TextView tagsview;
    TextView titleview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent myIntent = getIntent();
        postid = myIntent.getStringExtra("post_id");
        Log.d("postid", postid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpost);
        // prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        // prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        // prgDialog.setCancelable(false);
        errorMsg = (TextView) findViewById(R.id.login_error);

        // Get otp ET control value

        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        try {
            params.put("post_id", postid);
            Log.d("params",postid);
        } catch (Exception e) {

        }
        sendpostid(params);

    }

    public void sendpostid(RequestParams params){
        // Show Progress Dialog
        //   prgDialog.show();
        try {


            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://"+LoginActivity.ip+":8081/useraccount/post/description",params, new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {
                    // Hide Progress Dialog
                    //prgDialog.hide();
                    try {
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if (obj.getBoolean("status")) {
                            Log.d("status","true");
                            JSONArray A=obj.getJSONArray("jsonarray");
                            JSONObject objec = A.getJSONObject(0);
                            title = objec.getString("title");
                            description = objec.getString("description");
                            tags = objec.getString("tag_name");
                            emailid = objec.getString("email_id");

                            descriptionview = (TextView)findViewById(R.id.describe);
                            descriptionview.setText(description);
                            tagsview = (TextView)findViewById(R.id.tag);
                            tagsview.setText(tags);
                            emailidview = (TextView)findViewById(R.id.email);
                            emailidview.setText(emailid);
                            titleview = (TextView)findViewById(R.id.title);
                            titleview.setText(title);

                            Log.d("set", "true");

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
                    // prgDialog.hide();
                    Log.d("statuscode",Integer.toString(statusCode));
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


}