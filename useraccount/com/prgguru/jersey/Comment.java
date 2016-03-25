package com.prgguru.jersey;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

@Path("/comment")
public class Comment{
    
	//Map<String, String> map = new HashMap<String, String>();
    @POST
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/create_comment")  
    // Produces JSON as response
    //@Consumes .. try plss
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    // Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
    public String addComment(Map<String,String> map ){
    	boolean result = true; // returns if current user is valid or not... if null not logged in. 
    	String response="";
    	/*for (Map.Entry entry: map.entrySet())
    		System.out.println(entry.getKey() + ", " + entry.getValue());*/
    	String email_id=map.get("email");
    	String post=map.get("post_id");
    	String desc=map.get("description");
    	/*try {
             result = DBConnection.isLogin(email_id);
             //System.out.println("Inside checkCredentials try "+result);
         } catch (Exception e) {
             // TODO Auto-generated catch block
             //System.out.println("Inside checkCredentials catch");
             result = false;
         }  */
    	boolean retinsert=false;
    	String fields,table,clause,values;
    	if(result){
    		fields="post_id, user_id, description";
    		table = "comment";
            clause="where email_id = "+"\""+email_id+"\"";
            
            ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
            
    		try {
    				ret = DBConnection.select("user_id","user",clause);
    				//System.out.println("selected");
    				//System.out.println(ret);
    			}catch (Exception e) {
    			// TODO Auto-generated catch block
    				//System.out.println("cant select");
    				System.out.println(e);
    			}
    			if(ret.size()!=0){
    				try{
    					int id=ret.get(0).getInt("user_id");
    					//System.out.print("id is ");
    					//System.out.println(id);
    					values= post+" , " +Integer.toString(id) + " ,\"" + desc+"\"";
    					try{
    						retinsert=DBConnection.insert(fields, table, values);
    						//System.out.println("inserted");
    					}catch (Exception e) {
    						// TODO Auto-generated catch block
    						//e.printStackTrace();
    						//System.out.println("not inserted");
    						System.out.println(e);
    						
    					}
    					if(retinsert){
    						return  response = Utitlity.constructJSON("comment",true);
    					}
    				}catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
    					//System.out.println("cant get id");
    					System.out.println(e);
					}
    		
    			}
    	}
    	return response=Utitlity.constructJSON("comment", false);
    }
}
 
	
