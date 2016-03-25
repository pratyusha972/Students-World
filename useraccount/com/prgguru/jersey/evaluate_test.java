package com.prgguru.jersey;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
@Path("/test")
public class evaluate_test {
	// HTTP Get Method
    @POST
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/evaluate")
    // Produces JSON as response
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public String evaluate(Map<String,String> map){
        String response = "";
        System.out.println("In_evaluate");
        System.out.println(map);
        String uid=map.get("user_id");
        String cooky=map.get("cookies");
        if(!(DBConnection.Isverified(uid, cooky)))
        {
        	return Utitlity.constructJSON("evaluate", false, "unverified");
        }
        String topic_id=map.get("topic_id");
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
        String test_id="";
        try {
			test_id=exret.get(0).getString("test_id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
        String clause = "where test_question.question_id = questions.question_id and test_question.test_id="+test_id;
        String fields = "questions.question_id ,question , answer ,explanation";
        String tables = "questions , test_question";
        try {
			ret = DBConnection.select(fields,tables,clause);
			}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("ret="+ret);
        int score=0;
        for(int i=0;i<ret.size();i++)
        {
        	String question_id="",answer="";
        	try {
			 question_id=ret.get(i).getString("question_id");
			 answer=ret.get(i).getString("answer");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	System.out.println(answer+" "+question_id);
        	String user_answer=map.get(question_id);
        	if(answer.equals(user_answer))
        		score+=1;
        	try {
        		DBConnection.delete("test_history"," where question_id="+question_id+" and user_id="+uid);
				DBConnection.insert("question_id,user_id,user_response", "test_history",question_id+","+uid+",\""+user_answer+"\"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        JSONArray jsArray = new JSONArray(ret);
        try {
			DBConnection.update("test_user", "score",Integer.toString(score), "where user_id="+uid+" and test_id="+test_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Utitlity.constructJSON("evaluate", true,Integer.toString(score),jsArray);
    }
}
