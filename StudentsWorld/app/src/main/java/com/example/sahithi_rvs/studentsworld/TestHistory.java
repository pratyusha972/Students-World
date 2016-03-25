package com.example.sahithi_rvs.studentsworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sahithi_rvs on 21/11/15.
 */
public class TestHistory extends Activity implements AdapterView.OnItemClickListener  {
    SharedPreferences logged_in;
    SharedPreferences.Editor edit_login;
    int MAX_DATA_LENGTH=100;
    int flag=0;
    int[] tid = new int[MAX_DATA_LENGTH];
    public static String currentuser="";
    ArrayList<String> items = new ArrayList();
    ArrayList<String> items_again = new ArrayList();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_history);
        logged_in = getSharedPreferences("app", Context.MODE_PRIVATE);
        edit_login = logged_in.edit();
        String getStatus = logged_in.getString("login", "nil");
        currentuser = logged_in.getString("uid", "nil");
        edit_login.commit();
        if (getStatus.equals("false") || currentuser.equals("0")) {
            navigatetoLoginActivity();
            return;
        }

        RequestParams params = new RequestParams();
        try {
            params.put("user_id", currentuser);
            params.put("clause", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        invokeWS(params);
        ListView listView = (ListView) findViewById(R.id.list);
        // for(int i=0;i<items_again.size();++i) {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, items_again);
        listView.setAdapter(adapter);
        // }

    }

    public void invokeWS(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + LoginActivity.ip + ":8081/useraccount/testhistory/general", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.d("status", "true");
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        JSONArray data = obj.getJSONArray("jsonarray");
                        getjson(data);
                    } else {
                        Log.d("status", "false");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch bloc
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d("statuscode",""+statusCode);
                if (statusCode == 404) {
                    //         Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigatetoEvaluateActivity(Integer.toString(tid[position]));
    }

    public void getjson(JSONArray data) {

        String test;
        Log.d("datalength", Integer.toString(data.length()));
        for (int i = 0; i < data.length(); i++) {
            test="";
            try {
                JSONObject jobj = data.getJSONObject(i);
                test+="topic name:"+jobj.optString("topic_name")+"\n score:"+(jobj.optString("score"));
                Log.d("test",test);
                tid[i] = jobj.optInt("test_id");
                items.add(test);
                items_again.add(test);
                Log.d("awsdfsdrwf", "loop");
            } catch (Exception e) {

            }
        }
        Log.d("items", items.toString());
//        System.out.println(items.get(0));
        call();

    }

    public void call () {
        System.out.println("called");
        this.runOnUiThread(new Runnable() {
            public void run() {
                items_again.clear();
                items_again.addAll(items);
                Log.d("adapter", items_again.toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //myArrayList = Database.getSharedObject(getActivity()).getMyList();
        //  adapter.setItems (myArrayList);
        items_again.clear();
        items_again.addAll(items);
        adapter.notifyDataSetChanged();

    }

    public void navigatetoLoginActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    public void navigatetoEvaluateActivity(String testid){
      //  Intent loginIntent = new Intent(getApplicationContext(),EvaluateActivity.class);
        Intent i = new Intent(this, ViewPost.class);
        i.putExtra("test_id", testid);
        this.startActivity(i);

    }
}