package com.prgguru.jersey;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
@Path("/testhistory")
public class testhistory {
	@GET
	@Path("/general")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
	public String userhistory(@QueryParam("user_id") String uid)
	{
		String response="";
		ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
        try {
			ret = DBConnection.select("subject.subject_name,subtopics.topic_name,test_user.score","test_user,subtopics,subject,test","where test_user.user_id="+uid+" and test_user.test_id=test.test_id and test.topic_id=subtopics.topic_id and subject.subject_id=subtopics.subject_id");
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
	@GET
	@Path("/specific")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
	public String userhistory(@QueryParam("user_id") String uid,@QueryParam("test_id") String test_id)
	{
		String response="";
		String clause = "where test_question.question_id = questions.question_id and test_history.question_id=questions.question_id and test_question.test_id="+test_id+"and test_history.user_id="+uid;
        String fields = ",questions.question_id ,questions.question , questions.answer ,test_history.user_response,questions.explanation";
        String tables = "questions , test_question,test_history";
		ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
        try {
			ret = DBConnection.select(fields,tables,clause);
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
}
