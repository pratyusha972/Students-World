package com.example.sahithi_rvs.studentsworld.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sahithi_rvs.studentsworld.LoginActivity;
import com.example.sahithi_rvs.studentsworld.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends ListFragment implements AdapterView.OnItemClickListener {


    int MAX_DATA_LENGTH=100;
    int[] qid = new int[MAX_DATA_LENGTH];
    String TITLE="Questions";
    SharedPreferences logged_in;
    SharedPreferences.Editor edit_login;

    String testid="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        testid = getArguments().getString("TID");
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logged_in= getActivity().getSharedPreferences("app", 0);

        getActivity().setTitle(TITLE);

        edit_login = logged_in.edit();
        String getStatus=logged_in.getString("login", "nil");
        String currentuser=logged_in.getString("uid","nil");
        RequestParams params = new RequestParams();
        try {
            Log.d("awrwf", "canadsfme hereste2");
            params.put("user_id", currentuser);
            params.put("test_id",testid);
        } catch (Exception e) {

        }
        getjson(params);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + Integer.toString(qid[position]), Toast.LENGTH_SHORT).show();
    }

    public void getjson(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + LoginActivity.ip + ":8081/useraccount/test/getquestions", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d("onsucess", "success");
                System.out.println("qwertysdf");
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        Log.d("status", "true");
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
                //String name = jobj.optString("topic_name");
                qid[i]=jobj.optInt("question_id");
                String question=jobj.optString("question");
                items.add(question);
                String option_a=jobj.optString("option_a");
                Log.d("awsdfsdrwf", "loop");

            } catch (Exception e) {

            }

        }

        /*adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choiceP, items);
        setListAdapter(adapter);*/
        getListView().setOnItemClickListener(this);
    }



}
