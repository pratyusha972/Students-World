package com.prgguru.jersey;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


@Path("/test")
public class getQues {
	
	
	@GET
	@Path("/getquestion")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
	public String getData(@QueryParam("topic_id") String topic_id,@QueryParam("cookies") String cook,@QueryParam("user_id") String  uid) throws JSONException{
        System.out.println("req for getques");
		ArrayList<JSONObject > exret=new ArrayList<JSONObject >();
        try {
			exret = DBConnection.select("test_id","test","where topic_id="+topic_id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println(exret);
        if(!(DBConnection.Isverified(uid, cook)))
        {
        	return Utitlity.constructJSON("post", false, "unverified");
        }
        String test_id=exret.get(0).getString("test_id");
		String response = "";
        String clause = "where test_question.question_id = questions.question_id and test_question.test_id="+test_id+";";
        String fields = "questions.question_id ,question , option_a, option_b, option_c, option_d";
        String tables = "questions , test_question";
        ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
        
        try {
			ret = DBConnection.select(fields,tables,clause);
			}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(ret+"qu");
        String field_names="user_id,test_id";
        String table_name="test_user";
        String values=uid+","+test_id;
        boolean flag=false;
        try {
        	DBConnection.delete(table_name, "where test_id="+test_id+" and user_id="+uid);
        	flag=DBConnection.insert(field_names,table_name,values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ret.size()!=0 && flag==true){
			JSONArray jsArray = new JSONArray(ret);
			return (response = Utitlity.constructJSON("question",true,jsArray));
		}
		return (response = Utitlity.constructJSON("verified",false, "no such test id"));     
    }
	
	
	

}
