package com.example.sahithi_rvs.studentsworld.activity;


import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sahithi_rvs.studentsworld.LoginActivity;
import com.example.sahithi_rvs.studentsworld.QuestionActivity;
import com.example.sahithi_rvs.studentsworld.R;
import com.example.sahithi_rvs.studentsworld.RegisterActivity;
import com.example.sahithi_rvs.studentsworld.ViewPost;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class SubTopicFragment extends ListFragment implements AdapterView.OnItemClickListener {

    TextView errorMsg;
    // Name Edit View Object
    EditText queryET;
    // Email Edit View Object
    EditText descriptionET;
    // Passwprd Edit View Object
    EditText tagsET;
    Button button;
    ArrayAdapter adapter;

    int MAX_DATA_LENGTH=100;
    int[] tid = new int[MAX_DATA_LENGTH];
    String TITLE="Sub topics";
    SharedPreferences logged_in;
    SharedPreferences.Editor edit_login;
    String getStatus="";
    ;int height=0;
    String subid="";
    ArrayList<String> items = new ArrayList();
    ArrayList<String> items_again = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        subid = "1";
        View rootView = inflater.inflate(R.layout.fragment_createpost, container, false);
        Log.d("subtopic","came");
        queryET = (EditText)rootView.findViewById(R.id.query);
        // Find Email Edit View control by ID
        descriptionET = (EditText)rootView.findViewById(R.id.description);
        // Find Password Edit View control by ID
        tagsET = (EditText)rootView.findViewById(R.id.tags);
        button = (Button)rootView.findViewById(R.id.btnpost_submit);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                creationpost(v);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logged_in= getActivity().getSharedPreferences("app", 0);

       // getActivity().setTitle(TITLE);
        Log.d("subtopic", "came");
        edit_login = logged_in.edit();
         getStatus=logged_in.getString("login", "nil");
         String currentuser=logged_in.getString("uid","nil");
        RequestParams params = new RequestParams();
        try {
            params.put("user_id", currentuser);
        } catch (Exception e) {

        }
        getjson(params);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getActivity(), "Item: " + Integer.toString(tid[position]), Toast.LENGTH_SHORT).show();
        navigatetoViewPostActivity(Integer.toString(tid[position]));
        /*ListFragment test = new TestFragment();
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        Bundle args=new Bundle();
        args.putString("TID",Integer.toString(tid[position]));
        test.setArguments(args);
        ft.replace(android.R.id.content,test);
        ft.commit();*/
    }

    public void getjson(RequestParams params){
        Log.d("getParams","params");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + LoginActivity.ip + ":8081/useraccount/post/viewpost", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
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
    public void call () {
        System.out.println("error");
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                items_again.clear();
                items_again.addAll(items);
                Log.d("adapter",items_again.toString());
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void json_to_list(JSONArray data){
        Log.d("datalength", Integer.toString(data.length()));
        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject jobj = data.getJSONObject(i);
                String name = jobj.optString("title");
                tid[i]=jobj.optInt("post_id");
                items.add(name);
                Log.d("awsdfsdrwf", "loop");
                Log.d("awsdfsdrwf", name);
            } catch (Exception e) {

            }
        }

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public void creationpost(View view)
    {
       // Toast.makeText(getActivity(), "Your Query is posted :)", Toast.LENGTH_SHORT).show();
        String title = queryET.getText().toString();
        // Get Email ET control value
        String description = descriptionET.getText().toString();
        // Get Password ET control value
        String tags = tagsET.getText().toString();
        // Instantiate Http Request Param Object
        // RequestParams params = new RequestParams();
        JSONObject params = new JSONObject();
        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        //if(Utility.isNotNull(name) && Utility.isNotNull(email) && Utility.isNotNull(password)){
            // When Email entered is Valid
            //if(getStatus=="true" && currentuser!=0){
                if(true){
                // Put Http parameter name with value of Name Edit View control
                try {
                    params.put("user_id","63");
                    params.put("title", title);
                    // Put Http parameter username with value of Email Edit View control
                    params.put("description", description);
                    // Put Http parameter password with value of Password Edit View control
                    params.put("tags", tags);
                    items.add(title);
                    call();
                }catch (Exception e){

                }

                // Invoke RESTful Web Service with Http parameters
                invokeWS(params);
            }
            // When Email is invalid
            else{
                Toast.makeText(getActivity(), "Please login", Toast.LENGTH_LONG).show();
                navigatetoLoginActivity();
            }
        //}
        // When any of the Edit View control left blank
       /* else{
            Toast.makeText(getActivity(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }*/
   
    }

    public void invokeWS(JSONObject params) {
        // Show Progress Dialog 
      
        try {
            StringEntity entity = new StringEntity(params.toString());
            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(getActivity(), "http://"+LoginActivity.ip+":8081/useraccount/post/createpost", entity,"application/json" , new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {
                    // Hide Progress Dialog
                   
                    try {
                        // JSON Object
                        JSONObject obj = new JSONObject(response);

                        // When the JSON response has status boolean value assigned with true
                        if (obj.getBoolean("status")) {

                            //if (true){
                            // Set Default Values for Edit View controls
                            
                            setDefaultValues();
                            // Display successfully registered message using Toast

                            Toast.makeText(getActivity(), "successfully posted!", Toast.LENGTH_LONG).show();
                          
                        }
                        // Else display error message
                        else {
                            errorMsg.setText(obj.getString("error_msg"));
                            Toast.makeText(getActivity(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //navigatetoOtpActivity();
                        Toast.makeText(getActivity(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();


                    }
                }

                // When the response returned by REST has Http response code other than '200'
                @Override
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    // Hide Progress Dialog
                   Log.d("failed","404");
                    // When Http response code is '404'
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
        }catch (Exception e){

        }
    }

    public void navigatetoLoginActivity(){
        Intent loginIntent = new Intent(getActivity(),LoginActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    public void navigatetoViewPostActivity(String post_id){
        Intent i = new Intent(getActivity(), ViewPost.class);
        i.putExtra("post_id", post_id);
        getActivity().startActivity(i);
    }
    public void setDefaultValues(){
        queryET.setText("");
        descriptionET.setText("");
        tagsET.setText("");
    }
}
