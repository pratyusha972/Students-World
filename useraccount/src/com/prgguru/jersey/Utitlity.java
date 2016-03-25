package com.prgguru.jersey;
 
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
 
public class Utitlity {
    /**
     * Null check Method
     * 
     * @param txt
     * @return
     */
    public static boolean isNotNull(String txt) {
        // System.out.println("Inside isNotNull");
        return txt != null && txt.trim().length() >= 0 ? true : false;
    }
 
    /**
     * Method to construct JSON
     * 
     * @param tag
     * @param status
     * @return
     */
    public static String constructJSON(String tag, boolean status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    
    public static String constructJSON(String tag, boolean status,String uid,String cookies) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("user_id", uid);
            obj.put("cookies", cookies);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    public static String constructJSON(String tag, boolean status,String Score,JSONArray jsarray) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("Score", Score);
            obj.put("jsonarray", jsarray);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    
    public static String constructJSON(String tag,boolean status,JSONArray jsarray) {
        JSONObject obj = new JSONObject();
        try {
        	obj.put("tag", tag);
        	obj.put("status", new Boolean(status));
            obj.put("jsonarray", jsarray);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    public static JSONObject createJSON(String subject_name,JSONArray jsarray) {
        JSONObject obj = new JSONObject();
        try {
        	obj.put("subject_name", subject_name);
            obj.put("jsonarray", jsarray);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj;
    }
    
  /*  public static JSONObject constructJSON(ArrayList<String> array) {
        JSONObject obj = new JSONObject();
        try {
        	for(int i=0;i<array.size();i++){
        		obj.put(Integer.toString(i), array.get(i));
        	}
            
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj;
    }*/
    
    
    public static JSONObject constructJSON(ArrayList<String> names, ArrayList<String> value) {
        JSONObject obj = new JSONObject();
        try {
            for(int i=0;i<names.size();i++)
            {
            	obj.put(names.get(i), value.get(i));
            //	System.out.println("Hello");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
       // System.out.println("good");
        return obj;
    }
 
    /**
     * Method to construct JSON with Error Msg
     * 
     * @param tag
     * @param status
     * @param err_msg
     * @return
     */
    public static String constructJSON(String tag, boolean status,String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("error_msg", err_msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    
    public static String constructJSON(String tag, boolean status,ArrayList<JSONObject> ret) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("list",ret);      
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
 
}