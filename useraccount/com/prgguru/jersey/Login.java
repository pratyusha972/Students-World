package com.prgguru.jersey;
 
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.prgguru.jersey.encrypt;
//Path: http://localhost/<appln-folder-name>/login
@Path("/login")
public class Login {
    // HTTP Get Method
    @POST
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/dologin")
    // Produces JSON as response
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    
    // Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
    //public String doLogin(@QueryParam("username") String uname, @QueryParam("password") String pwd){
    public String doLogin(Map<String,String> map){
        String response = "";
        String uid="";
        String uname=map.get("username");
    	String pwd=map.get("password");
    	System.out.println("here");
        ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
        String salt="";	
        
       
        try {
				ret=DBConnection.select("*", "user","where email_id=\""+uname+"\"" );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
        
        try{
        	salt=ret.get(0).getString("cook");
        	uid=ret.get(0).getString("user_id");
        	
        }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String cookies = encrypt.getSecurePassword(uname, salt);
        System.out.println(ret);
        
        try {
			if(ret.get(0).getString("email_id").equals(uname) && ret.get(0).getString("password").equals(pwd) && ret.get(0).getString("valid").equals("true")){
				/*Random RandomGenerator=new Random();
	            int coo=RandomGenerator.nextInt(10457853);
	            String cook=Integer.toString(coo);
	            try {
					DBConnection.update("user","cook",cook,"where email_id=\""+uname+"\"");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			    response = Utitlity.constructJSON("login",true,uid,cookies);
			}else{
			    response = Utitlity.constructJSON("login", false, "Incorrect Email or Password");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    return response;        
    }
}