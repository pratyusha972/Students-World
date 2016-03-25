package com.prgguru.jersey;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

@Path("/post")
public class Post {
    
	//Map<String, String> map = new HashMap<String, String>();
    @POST
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/createpost")  
    // Produces JSON as response
    //@Consumes .. try plss
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    // Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
    public String addPost(Map<String,String> map ){
   // public String addPost(@QueryParam("email") String email_id,@QueryParam("title") String title,@QueryParam("description") String descr,@QueryParam("tags") String tags){
    	boolean result = true; // returns if current user is valid or not... if null not logged in. 
    	String tid="";
    	String response="";
    	System.out.println("got it");
    	/*for (Map.Entry entry: map.entrySet())
    		System.out.println(entry.getKey() + ", " + entry.getValue());*/
    	String uid=map.get("user_id");
    	String cook=map.get("cookies");
    	String title=map.get("title");
    	String descr=map.get("description");
    	String tags=map.get("tags");
    	/*if(!(DBConnection.Isverified(uid, cook)))
        {
        	return Utitlity.constructJSON("post", false, "unverified");
        }
        */
    	List<String> items = Arrays.asList(tags.split("\\s*,\\s*"));
    	/*try {
             result = DBConnection.isLogin(email_id);
             //System.out.println("Inside checkCredentials try "+result);
         } catch (Exception e) {
             // TODO Auto-generated catch block
             //System.out.println("Inside checkCredentials catch");
             result = false;
         }  */
    	System.out.println(items);
    	boolean retinsert=false;
    	boolean rettag=true;
    	String fields,table,clause,values;
    		fields="user_id,title , description";
    		table = "post";
            clause="where user_id= "+"\""+uid+"\"";
            
            ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
            /*
    		try {
    				ret = DBConnection.select("user_id","user",clause);
    				//JSONArray jsArray = new JSONArray(ret);
    				System.out.println("selected");
    				System.out.println(ret);
    			}catch (Exception e) {
    			// TODO Auto-generated catch block
    				System.out.println("cant select");
    				System.out.println(e);
    			}
    			if(ret.size()!=0){
    				try{
    					int id=ret.get(0).getInt("user_id");
    					System.out.print("id is ");
    					System.out.println(id);
    					*/
            ArrayList<JSONObject > newret=new ArrayList<JSONObject >();
    					values= uid+" , \""+ title + "\" ,\"" + descr+"\"";
    					try{
    						retinsert=DBConnection.insert(fields, table, values);
    						
    		    			for(String item : items){
    		    				System.out.println("item"+item);
    		    				if(item.equals(""))
    		    					continue;
    		    				item=item.toLowerCase();
    		    				String claus=" where tag_name = \"" + item + "\"";
    		     				ret=DBConnection.select("tag_id","tag",claus);
    		     				tid=ret.get(0).getString("tag_id");
    		     		        ArrayList<JSONObject > exret=new ArrayList<JSONObject >();
    		     		        try {
    		     					exret = DBConnection.select("max(post_id)","post","where user_id="+uid+" group by user_id");
    		     				} catch (SQLException e1) {
    		     					// TODO Auto-generated catch block
    		     					e1.printStackTrace();
    		     				} catch (Exception e1) {
    		     					// TODO Auto-generated catch block
    		     					e1.printStackTrace();
    		     				}
    		     		        System.out.println(exret);
    		     		        String postid=exret.get(0).getString("max(post_id)");
    		     				String flds= "post_id , tag_id";
    		     				String vals=postid + " , "+tid;
    		     				rettag=DBConnection.insert(flds, "post_tag", vals);
    		     				if(!rettag)
    		     					break;
    		    			}
    						System.out.println("inserted");
    					}catch (Exception e) {
    						// TODO Auto-generated catch block
    						//e.printStackTrace();
    						System.out.println("not inserted");
    						System.out.println(e);
    						
    					}
    					if(retinsert && rettag){
    						return  response = Utitlity.constructJSON("post",true);
    					}
    	return response=Utitlity.constructJSON("post", false);
    }
    
    @GET
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/viewpost")  
    // Produces JSON as response
    //@Consumes .. try plss
   // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
     public String viewPost(@QueryParam("user_id") String user_id){
    	String response="";
    	/*String email_id=map.get("email");
    	String fields=map.get("fields");
    	String table=map.get("table");
    	String clause=map.get("clause");*/
    	String fields="*";
    	String table="post";
    	String clause="";
    	
    
    	ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
    	try {
			ret = DBConnection.select(fields,table,clause);
			JSONArray jsArray = new JSONArray(ret);
			System.out.println(ret);
			return  response = Utitlity.constructJSON("post",true,jsArray);
		}catch (Exception e) {
		// TODO Auto-generated catch block
			System.out.println("cant select");
			System.out.println(ret);
			System.out.println(e);
		}
    	
    	return response=Utitlity.constructJSON("view post", false);
    }
}
 
	
