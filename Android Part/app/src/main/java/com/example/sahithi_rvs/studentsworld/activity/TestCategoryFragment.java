/*package com.example.sahithi_rvs.studentsworld.activity;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.sahithi_rvs.studentsworld.LoginActivity;
import com.example.sahithi_rvs.studentsworld.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

public class TestCategoryFragment extends ListFragment implements OnItemClickListener {

    int MAX_DATA_LENGTH=100;
    int[] sid = new int[MAX_DATA_LENGTH];

    SharedPreferences logged_in;
    SharedPreferences.Editor edit_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logged_in= getActivity().getSharedPreferences("app", 0);
        Log.d("gotit", "canadsfme hereste2");
        edit_login = logged_in.edit();
        String getStatus=logged_in.getString("login", "nil");
        int currentuser=logged_in.getInt("uid",0);
        RequestParams params = new RequestParams();
        try {
            Log.d("awrwf", "canadsfme hereste2");
            params.put("user_id", Integer.toString(currentuser));
            params.put("clause","");
        } catch (Exception e) {

        }
        getjson(params);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + Integer.toString(sid[position]), Toast.LENGTH_SHORT).show();
        //String cid=id.getText().toString();
        ListFragment subtopic = new SubTopicFragment();
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        Bundle args=new Bundle();
        args.putString("SID",Integer.toString(sid[position]));
        subtopic.setArguments(args);
        ft.replace(android.R.id.content,subtopic);
        ft.commit();

    }

    public void getjson(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + LoginActivity.ip + ":8081/useraccount/test/getcategories", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d("onsucess", "success");
                System.out.println("qwertysdf");
                try {
                    // JSON Object
                    Map<String,ArrayList<String>> ret=new Map<ArrayList<String>>();

                    JSONObject obj = new JSONObject(response);
                    JSONArray data=obj.getJSONArray("jsonarray");
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        Log.d("status", "true");
                    }
                    String subject_name = obj.optString("name");
                    for (int i = 0; i < data.length(); i++) {
                        try {

                            JSONObject jobj = data.getJSONObject(i);
                            JSONArray list=obj.getJSONArray("jsonarray");
                            ArrayList<String>
                            if(flag)
                                    json_to_list(jobj.getJSONArray("subsubjects)
                                            items.add(name);
                                Log.d("awsdfsdrwf", "loop");
                                Log.d("awsdfsdrwf", name);

                            } catch (Exception e) {

                            }

                            json_to_list(obj.getJSONArray("jsonarray"));
                    } else {
                        Log.d("status", "false");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch bloc
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                Log.d("sdfsdf", Integer.toString(statusCode));
                Log.d("onfailure", Integer.toString(statusCode));

                if (statusCode == 404) {
                    Toast.makeText(getActivity(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                     Toast.makeText(getActivity(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                     Toast.makeText(getActivity(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void json_to_list(JSONArray data){
        ArrayList<String> items = new ArrayList();
        Log.d("datalength", Integer.toString(data.length()));
        for (int i = 0; i < data.length(); i++) {
            try {

                JSONObject jobj = data.getJSONObject(i);
            for(int j=0;j<jobj.length();j++) {
                JSONObject jobj1 = jobj[i]

                String subject_name = jobj.optString(name);
                if(flag)
                    json_to_list(jobj.getJSONArray("subsubjects)
                            items.add(name);
                Log.d("awsdfsdrwf", "loop");
                Log.d("awsdfsdrwf", name);

            } catch (Exception e) {

            }

        }


        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

}*/