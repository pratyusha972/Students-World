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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class QuestionActivity extends Activity {
    TextView questiono;
    // Progress Dialog Objecttopic_id"
   // ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    ProgressDialog prgDialog;
    // Email Edit View Object
    JSONArray questions;
    HashMap<String,String > Response=new HashMap<String ,String >();
    ArrayList<JSONObject> toputinans = new ArrayList<JSONObject>();
    String topic_id="",user_id="";
    SharedPreferences logged_in;
    SharedPreferences.Editor edit_login;
    String getStatus,cookies;
    HashMap<Integer,String> map=new HashMap<Integer,String >();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logged_in= getSharedPreferences("app", 0);
        edit_login = logged_in.edit();
        Intent myIntent = getIntent();
        topic_id = myIntent.getStringExtra("topic_id");
        getStatus = logged_in.getString("login", "nil");
        String currentuser=logged_in.getString("uid","nil");
        cookies=logged_in.getString("cookies", "nil");
        user_id = currentuser;
        Log.d("cookies", cookies);
        if (getStatus.equals("false")){
            navigatetoLoginActivity();
            return;
        }

        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False

        prgDialog.setCancelable(false);
        setContentView(R.layout.question);
        questiono = (TextView)findViewById(R.id.quesno);

        try {
            RequestParams params = new RequestParams();
            params.put("topic_id", topic_id);
            params.put("user_id", currentuser);
            params.put("cookies", cookies);
            invokeWS(params);
        }catch (Exception e){

        }

    }

    public void invokeWS(RequestParams params){
        prgDialog.show();
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://10.0.2.2:8081/useraccount/test/getquestion", params, new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {
                    try {
                        prgDialog.hide();
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if (obj.getBoolean("status")) {
                            questions = obj.getJSONArray("jsonarray");
                            JSONObject jobj;
                            for (int i = 0; i < questions.length(); i++) {
                                try {

                                    jobj = questions.getJSONObject(i);
                                    String question_id = jobj.getString("question_id");
                                    Response.put(question_id,"NA");
                                    //question.setText(jobj.getString("question"));
                                    //String a=jobj.getString("question_id");
                                    //map.put(jobj.getString(question_id), a);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            showquestion(0);
                        } else {
//                            errorMsg.setText(obj.getString("error_msg"));
                            String errortag = obj.getString("error_msg");
                            if (errortag == "unverified") {
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
                    Log.d("sdfsdf", Integer.toString(statusCode));
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

    public void showquestion(final int quesno){
        questiono.setText(Integer.toString(quesno+1));
        if(questions.length() <= quesno){
            return;
        }
        else if (quesno < 0){
            return;
        }
        JSONObject jobj = new JSONObject();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.choices);
        TextView question = (TextView)findViewById(R.id.question);
        final TextView yourresponse = (TextView)findViewById(R.id.your_response);
        try {
            jobj = questions.getJSONObject(quesno);
            question.setText(jobj.getString("question"));
            String a=jobj.getString("question_id");
            String question_id = Response.get(a);
            yourresponse.setText(question_id);
            map.put(quesno, a);
            for (int i = 0; i < 4; i++)
                ((RadioButton) radioGroup.getChildAt(i)).setText(jobj.optString("option_" + (char) ((int) 'a' + i)));

        }
        catch (Exception e){
            e.printStackTrace();
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radiogroup, int checkedId) {

                int index;
                RadioButton radioButton;
                switch(checkedId) {
                    case R.id.option_a:
                        index = 0;
                        //radioButton = (RadioButton)findViewById(R.id.option_a);
                        //yourresponse.setText((int)'a' + index);
                        break;
                    case R.id.option_b:
                        index = 1;
                        //radioButton = (RadioButton)findViewById(R.id.option_b);
                        //yourresponse.setText(radioButton.getText());
                        break;
                    case R.id.option_c:
                        index = 2;
                        //radioButton = (RadioButton)findViewById(R.id.option_c);
                        //yourresponse.setText(radioButton.getText());
                        break;
                    case R.id.option_d:
                        index = 3;
                        //radioButton = (RadioButton)findViewById(R.id.option_d);
                        //yourresponse.setText(radioButton.getText());
                        break;
                    default:
                        index = -1;
                }
                if (index != -1) {
                    try {
                        int b;
                        char c;
                        if (index != -1) {
                            b = (int) 'a' + index;
                            c = (char) b;
                        } else {
                            c = (char) 'n';
                        }
                        yourresponse.setText(Character.toString(c));
                        Response.put(map.get(quesno), Character.toString(c));
                        //  toputinans.add(tempans);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Response.remove(quesno);
                }
            }
        });

        Button btnprev = (Button) findViewById(R.id.prev);
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.choices);
                //int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
                //JSONObject tempans = new JSONObject();
                try {
                    radioGroup.clearCheck();
                    showquestion(quesno - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnnext = (Button) findViewById(R.id.next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.choices);
                int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
                // JSONObject tempans = new JSONObject();
                try {
                    radioGroup.clearCheck();
                    showquestion(quesno + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void endtest(View view){

        try{
           // prgDialog.show();
            final JSONObject jsonParams = new JSONObject();
            for (HashMap.Entry<String, String> entry : Response.entrySet()) {
                try {
                    jsonParams.put(entry.getKey(), entry.getValue());
                }
                catch (Exception e) {
                }
            }
            jsonParams.put("cookies",cookies);
            jsonParams.put("user_id",user_id);
            jsonParams.put("topic_id", topic_id);
            StringEntity entity = new StringEntity(jsonParams.toString());
            //entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(getApplicationContext(), "http://"+LoginActivity.ip+":8081/useraccount/test/evaluate", entity, "application/json",new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(String response) {
                    // Hide Progress Dialog
                   // prgDialog.hide();
                    try {
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if(obj.getBoolean("status")){
                            JSONArray correctans = obj.getJSONArray("jsonarray");
                            String score = obj.getString("Score");
                            evaluate(correctans,score);
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
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void uncheck(){
        final RadioButton radiobutton = (RadioButton) findViewById(R.id.option_a);
        radiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radiobutton.isChecked()){
                    radiobutton.setChecked(false);
                }
                else {
                    radiobutton.setChecked(true);
                }
            }
        });
    }

    public void evaluate(JSONArray correctans,String score){
        navigatetoEvaluateActivity(correctans, score);
    }

    public void navigatetoEvaluateActivity(JSONArray correctans, String score){
        Intent evalintent = new Intent(getApplicationContext(), EvaluateActivity.class);
        evalintent.putExtra("correctans",correctans.toString());
        evalintent.putExtra("response", Response);
        evalintent.putExtra("score", score);
        startActivity(evalintent);
    }

    public void navigatetoLoginActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }
}
