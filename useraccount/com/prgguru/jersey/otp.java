package com.prgguru.jersey;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
@Path("/otp")
public class otp {
	@POST
	@Path("/verify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	 //public String verify(@QueryParam("otp") String otp, @QueryParam("username") String email_id){
      public String verify(Map<String,String> map){
		String email_id=map.get("username");
		String otp=map.get("otp");
		String response = "";
        String clause="where email_id = "+"\""+email_id+"\"";
        System.out.println("request came otp");
        ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
		try {
			ret = DBConnection.select("otp,user_id","user",clause);
			}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ret.size()!=0){
			String a=new String("otp");
			String user_id="";
			try {
				user_id=ret.get(0).getString("user_id");
				if(ret.get(0).getString("otp").equals(otp)){
					try {
						DBConnection.update("user","valid","1","where email_id=\""+email_id+"\"");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				String salt="",cookies="";
				try {
					salt = encrypt.getSalt();
					cookies=encrypt.getSecurePassword(email_id,salt);
			        
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
					return (response = Utitlity.constructJSON("verified",true,user_id,cookies));
			}
				else
					return (response = Utitlity.constructJSON("verified",false, "your otp do not match"));     
		    }
				
		catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
		return (response = Utitlity.constructJSON("verified",false, "your otp do not match"));     

 }
	
}
