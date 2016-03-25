package com.prgguru.jersey;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/profile")
public class viewprofile {
	@GET
	@Path("/viewprofile")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
	public String getData(@QueryParam("user_id") String uid)
	{
		String response="";
	/*	ArrayList<JSONObject > newret=new ArrayList<JSONObject >();
        try {
			newret = DBConnection.select("user_id","user","where email_id=\""+uname+"\"");
			}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String uid="";
        try {
			uid=newret.get(0).getString("user_id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("user_id"+uid);
		ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
        try {
			ret = DBConnection.select("*","user_profile","where id="+uid);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println(ret);

	JSONArray jsArray = new JSONArray(ret);
	return (response = Utitlity.constructJSON("profile",true,jsArray));
	}
	// HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/editprofile")
    // Produces JSON as response
   // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
   // public String changeprofile(Map<String,String> map){
    public String changeprofile(@QueryParam("firstname") String firstname,@QueryParam("lastname") String lastname,@QueryParam("degree") String degree,@QueryParam("start_degree") String start_deg,@QueryParam("end_degree") String end_deg,@QueryParam("dob") String dob,@QueryParam("address") String add,@QueryParam("college_name") String col,@QueryParam("mobile") String mob,@QueryParam("id") String id,@QueryParam("cookies") String cook){
        System.out.println("came in");
    	String response = "";
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("firstname", firstname);
        map.put("lastname", lastname);
        map.put("degree", degree);
        map.put("start_degree",start_deg);
        map.put("end_degree",end_deg);
        map.put("dob", dob);
        map.put("address", add);
        map.put("college_name", col);
        map.put("mobile",mob);
        System.out.println(map);
        map.put("id",id);
        map.put("cookies",cook);
        String uid=map.get("id");
        System.out.println(id);
        System.out.println(map);
        String cooky=map.get("cookies");
      /*  if(!(DBConnection.Isverified(uid, cooky)))
        {
        	return Utitlity.constructJSON("editprofile", false, "unverified");
        }*/
        for(HashMap.Entry<String, String> entry : map.entrySet())
        {
        	if(entry.getKey().equals("id") || entry.getKey().equals("cookies"))
        		continue;
        	try {
				DBConnection.update("user_profile", entry.getKey(), entry.getValue(),"where id="+uid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        response = Utitlity.constructJSON("editprofile",true,"redirect to myprofile");
        System.out.println("response "+ response);
        return response; 
       }
}
