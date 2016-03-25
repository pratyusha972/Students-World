package com.example.sahithi_rvs.studentsworld.activity;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import java.net.*;
import java.io.*;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sahithi_rvs.studentsworld.LoginActivity;
import com.example.sahithi_rvs.studentsworld.QuestionActivity;
import com.example.sahithi_rvs.studentsworld.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SubjectFragment extends ListFragment {
    FragmentActivity listener;
    TopicsListAdapter adp = new TopicsListAdapter();
    int MAX_DATA_LENGTH=100;
    public volatile int flag=0;
    ArrayList< ArrayList<Integer> > qid = new ArrayList<ArrayList<Integer>>();
    int[] tid = new int[MAX_DATA_LENGTH];
    SharedPreferences logged_in;
    SharedPreferences.Editor edit_login;
    int currentuser;
    ArrayList<String> subjects = new ArrayList();
    View v;
    ArrayList<ArrayList<String> > subtopics = new ArrayList<ArrayList<String> >();

    public void navigatetoQuestionActivity(int subid){
        String subjectid = Integer.toString(subid);
        Intent i = new Intent(getActivity(), QuestionActivity.class);
        i.putExtra("test_id", subjectid);
        getActivity().startActivity(i);
    }

    class RunnableDemo implements Runnable {
        private Thread t;
        private String threadName;

        RunnableDemo( String name){
            threadName = name;
        }
        public void run() {
            logged_in= getActivity().getSharedPreferences("app", 0);
            edit_login = logged_in.edit();
            String getStatus=logged_in.getString("login", "nil");
            currentuser=logged_in.getInt("uid", 0);
            RequestParams params = new RequestParams();
            try {
                params.put("user_id", Integer.toString(currentuser));
                params.put("clause","");
            } catch (Exception e) {
                e.printStackTrace();
            }

            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://" + LoginActivity.ip + ":8081/useraccount/test/getcategories", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    try {
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if (obj.getBoolean("status")) {
                            JSONArray data = obj.getJSONArray("jsonarray");
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject jobj = data.getJSONObject(i);
                                    String name = jobj.optString("subject_name");
                                    JSONArray sub_top_js = jobj.getJSONArray("jsonarray");
                                    subjects.add(name);
                                    ArrayList<String> AA = new ArrayList<String>();
                                    ArrayList<Integer> idd = new ArrayList<Integer>();
                                    for (int j = 0; j < sub_top_js.length(); j++) {
                                        JSONObject newobj = sub_top_js.getJSONObject(j);
                                        String sub = newobj.getString("topic_name");
                                        String id = newobj.getString("topic_id");
                                        idd.add(Integer.parseInt(id));
                                        AA.add(sub);
                                    }
                                    subtopics.add(AA);
                                    qid.add(idd);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                flag = 1;
                            }
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

        public void start ()
        {
            System.out.println("Starting " + threadName);
            if (t == null)
            {
                t = new Thread (this, threadName);
                t.start ();
                try {
                    t.join();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void main() {
            RunnableDemo R1 = new RunnableDemo( "Thread-1");
            R1.start();
    }

    @Override
    public void onAttach(Activity MaindupActivity){
        super.onAttach(MaindupActivity);
        this.listener = (FragmentActivity) MaindupActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIstanceState) {
        main();
        System.out.println("acme to render view");
        v = inflater.inflate(R.layout.fragment_subject, null);
        ExpandableListView elv = (ExpandableListView) v.findViewById(android.R.id.list);
        //elv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
      //  int numberOfChildToCheck = elv.getChildCount();
    //    elv.setItemChecked(numberOfChildToCheck+1, true);
        elv.setAdapter(adp);
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                TextView tv = (TextView) v.findViewById(R.id.childTextView);
                int subid = qid.get(groupPosition).get(childPosition);
                navigatetoQuestionActivity(subid);
                return true;
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class TopicsListAdapter extends BaseExpandableListAdapter  implements ExpandableListView.OnChildClickListener  {

        @Override
        public int getGroupCount() {
            return subjects.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return subtopics.get(i).size();
        }

        @Override
        public Object getGroup(int i) {
            return subjects.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return subtopics.get(i).get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(SubjectFragment.this.getActivity());
            textView.setText(getGroup(i).toString());
            return textView;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(SubjectFragment.this.getActivity());
            textView.setText(getChild(i, i1).toString());
            return textView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            //System.out.println("selected");
            return true;
        }

        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {
            Toast.makeText(getActivity(), "Clicked On Child",
                    Toast.LENGTH_SHORT).show();
            System.out.println("clicked");
            return true;
        }
    }
}