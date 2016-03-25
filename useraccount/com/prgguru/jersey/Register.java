package com.prgguru.jersey;
 
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
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

import org.codehaus.jettison.json.JSONObject;

import com.prgguru.jersey.encrypt;
//Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class Register {
    // HTTP Get Method
    @POST
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/doregister")  
    // Produces JSON as response
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
    //public String doLogin(@QueryParam("name") String name, @QueryParam("username") String uname, @QueryParam("password") String pwd){
    public String doregister(Map<String,String> map) throws SQLException, Exception{
            String response = "";
            
            String name=map.get("name");
        	String uname=map.get("username");
        	String pwd=map.get("password");    
   
        //System.out.println("Inside doLogin "+uname+"  "+pwd);
        String salt="",cookies="";
		try {
			salt = encrypt.getSalt();
			cookies=encrypt.getSecurePassword(uname,salt);
	        
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        int retCode = registerUser(name, uname, pwd,salt);
        if(retCode == 0){
        	Send mail= new Send(uname);
        	/*Random RandomGenerator=new Random();
            int coo=RandomGenerator.nextInt(10457853);
            String cook=Integer.toString(coo);
            try {
				DBConnection.update("user","cook",cook,"where email_id=\""+uname+"\"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        	mail.sendm();
        	ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
        	ret=DBConnection.select("user_id","user","where email_id=\""+uname+"\"");
        	String uid=ret.get(0).getString("user_id");
        	
            response = Utitlity.constructJSON("register",true,"enterotp");
        }else if(retCode == 1){
            response = Utitlity.constructJSON("register",false, "You are already registered");
        }else if(retCode == 2){
            response = Utitlity.constructJSON("register",false, "Special Characters are not allowed in Username and Password");
        }else if(retCode == 3){
            response = Utitlity.constructJSON("register",false, "Error occured");
        } System.out.println("response "+ response);
        return response;
 
    }
 
    private int registerUser(String name, String uname, String pwd,String salt){
        System.out.println("Inside checkCredentials");
        int result = 3;
        if(Utitlity.isNotNull(uname) && Utitlity.isNotNull(pwd)){
            try {
                if(DBConnection.insertUser(name, uname, pwd,salt)){
                    System.out.println("RegisterUSer if");
                    result = 0;
                }
            } catch(SQLException sqle){
            	System.out.println(sqle);
                System.out.println("RegisterUSer catch sqle");
                //When Primary key violation occurs that means user is already registered
                if(sqle.getErrorCode() == 1062){
                    result = 1;
                } 
                //When special characters are used in name,username or password
                else if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    result = 2;
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
            	System.out.println(e);
                System.out.println("Inside checkCredentials catch e ");
                result = 3;
            }
        }else{
            System.out.println("Inside checkCredentials else");
            result = 3;
        }
 
        return result;
    }
 
}