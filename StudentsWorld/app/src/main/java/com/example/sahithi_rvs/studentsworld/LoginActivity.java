package com.example.sahithi_rvs.studentsworld;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.sahithi_rvs.studentsworld.R;
import com.example.sahithi_rvs.studentsworld.activity.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.app.ProgressDialog;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Locale;


public class LoginActivity extends Activity {
    public static String ip="10.42.0.1";
    SharedPreferences logged_in;
    SharedPreferences.Editor edit_login;            // Progress Dialog Object
    ProgressDialog prgDialog;                       // Error Msg TextView Object
    TextView errorMsg;                              // Email Edit View Object
    EditText emailET;                               // Passwprd Edit View Object
    EditText pwdET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logged_in= getSharedPreferences("app", 0);
        edit_login = logged_in.edit();

        String getStatus=logged_in.getString("login", "nil");
        String currentuser=logged_in.getString("uid", "nil");
        String cookies=logged_in.getString("cookies","nil");
        System.out.println("cametohere");
        System.out.println("status "+ getStatus);
        if(getStatus.equals("true")){
            System.out.println("cametohere");
            navigatetoMainActivity();
            return;
        }

        setContentView(R.layout.login);
        // Find Error Msg Text View control by ID
        errorMsg = (TextView)findViewById(R.id.login_error);
        // Find Email Edit View control by ID
        emailET = (EditText)findViewById(R.id.loginEmail);
        // Find Password Edit View control by ID
        pwdET = (EditText)findViewById(R.id.loginPassword);
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
    }


    /**
     * Method gets triggered when Login button is clicked
     *
     * @param view
     */
    public void loginUser(View view){
        // Get Email Edit View Value
        String email = emailET.getText().toString();
        // Get Password Edit View Value
        String password = pwdET.getText().toString();
        // Instantiate Http Request Param Object
        JSONObject params = new JSONObject();

        //RequestParams params = new RequestParams();
        // When Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull(email) && Utility.isNotNull(password)){
            // When Email entered is Valid
            if(Utility.validate(email)){
                // Put Http parameter username with value of Email Edit View control
                try {
                    params.put("username", email);
                    // Put Http parameter password with value of Password Edit Value control
                    params.put("password", password);

                }catch (Exception e){

                }
                // Invoke RESTful Web Service with Http parameters
                invokeWS(params);
            }
            // When Email is invalid
            else{
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public void invokeWS(JSONObject params){
        // Show Progress Dialog
        prgDialog.show();

        try {
            StringEntity entity = new StringEntity(params.toString());
            System.out.println(entity);
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getApplicationContext(),"http://10.42.0.1:8081/useraccount/login/dologin",entity,"application/json" ,new AsyncHttpResponseHandler() {
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

                        edit_login.putString("login", "true");
                        edit_login.putString("uid", obj.getString("user_id"));
                        edit_login.putString("cookies",obj.getString("cookies"));
                        edit_login.commit();
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        // Navigate to Home screen
                        navigatetoMainActivity();
                    }
                    // Else display error message
                    else{
                        errorMsg.setText(obj.getString("error_msg"));
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

    /**
     * Method which navigates from Login Activity to Home Activity
     */
    public void navigatetoMainActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),MaindupActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    /**
     * Method gets triggered when Register button is clicked
     *
     * @param view
     */
    public void navigatetoRegisterActivity(View view){
        Intent loginIntent = new Intent(getApplicationContext(),RegisterActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }
}
