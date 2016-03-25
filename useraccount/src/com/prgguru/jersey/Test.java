package com.prgguru.jersey;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

@Path("/test")
public class Test {
	
	@GET
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/getcategories")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
	public String getCategories(){
		String response="";
		JSONArray jsArray2 = new JSONArray();
		ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
    	try {
            String fields = "*";
            String table = "subject";
			ret = DBConnection.select(fields,table,"");
			System.out.println(ret);
			for(int i=0;i!=ret.size();i++)
			{
				ArrayList<JSONObject > newret=new ArrayList<JSONObject >();
				JSONObject obj=ret.get(i);
				String  Subjectid = obj.getString("subject_id");
				String  Subject_name = obj.getString("subject_name");
				String f="*";
				String t="subtopics";
				String c="where subject_id="+Subjectid;
				newret = DBConnection.select(f,t,c);
				JSONArray jsArray = new JSONArray(newret);
				JSONObject newobj=Utitlity.createJSON(Subject_name,jsArray);
				jsArray2.put(newobj);
			}
			System.out.println(jsArray2);
			return  response = Utitlity.constructJSON("topics",true,jsArray2);
		}catch (Exception e) {
		// TODO Auto-generated catch block
			System.out.println("cant select");
			System.out.println(ret);
			System.out.println(e);
		}
    	
    	return response=Utitlity.constructJSON("categories", false);
    }
}
	