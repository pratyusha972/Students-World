package com.example.sahithi_rvs.studentsworld.activity;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sahithi_rvs.studentsworld.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends ListFragment {
    JSONArray arr;
    ListView listview;
    int k=0;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("awrwf","1st");
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        //listview=(ListView) rootView.findViewById(R.id.medicines_list);
        Log.d("awrwf","canme her");
        /*setListAdapter(new ArrayAdapter(
                this,android.R.layout.simple_list_item_1,
                this.populate()));*/
        // Inflate the layout for this fragment
        JSONObject params = new JSONObject();
        /*try {
            Log.d("awrwf","canadsfme hereste2");
            params.put("email", "adsf");
            params.put("fields", "*");
            params.put("table", "post");
            params.put("clause", "");

        }catch (Exception e){

        }*/
        JSONArray data;
        ArrayList<String> items= new ArrayList();
        /*data=getjson(params);

        Log.d("datalength", Integer.toString(data.length()));
        for(int i=0;i<data.length();i++){
            try {

                JSONObject jobj=data.getJSONObject(i);
                String title=jobj.optString("title");
                String descr=jobj.optString("description");
                items.add(title);

                Log.d("awsdfsdrwf", "loop");
                Log.d("awsdfsdrwf", title);
            }catch (Exception e){

            }

        }*/

        items.add("bhakti");

        items.add("sahithi");
        items.add("shruti");
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };
        Log.d("bhakti", "huu7y");
        //setListAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, values));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        Log.d("qwe13","bye");
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public JSONArray getjson(JSONObject params){

        try {
            StringEntity entity = new StringEntity(params.toString());
            System.out.println(entity);
            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d("qwe13","basynchttp");
            client.post(getActivity(),"http://10.42.0.52:8081/useraccount/post/viewpost",entity,"application/json" ,new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'

                @Override
                public void onSuccess(String response) {
                    k=1;
                    // Hide Progress Dialog
                    Log.d("onsucess", "success");

                    System.out.println("qwertysdf");

                    try {
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if(obj.getBoolean("status")){
                            Log.d("status","true");
                            arr= obj.getJSONArray("jsonarray");
                            // Navigate to Home screen

                        }
                        // Else display error message
                        else {
                            Log.d("status","false");
                            //errorMsg.setText(obj.getString("error_msg"));

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch bloc
                        e.printStackTrace();

                    }
                }

                // When the response returned by REST has Http response code other than '200'
                @Override
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    k=2;
                    Log.d("sdfsdf", Integer.toString(statusCode));
                    Log.d("onfailure", Integer.toString(statusCode));
                    // Hide Progress Dialog

                    // When Http response code is '404'
                    if(statusCode == 404){
                        //Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code is '500'
                    else if(statusCode == 500){
                        // Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code other than 404, 500
                    else{
                        // Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e){

        }
        Log.d("return","aserr");
        Log.d("k",Integer.toString(k));
        System.out.println(arr);
        return arr;
    }
}
