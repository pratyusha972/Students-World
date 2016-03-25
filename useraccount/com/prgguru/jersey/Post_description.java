package com.prgguru.jersey;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

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
public class Post_description {
	@GET
	@Path("/description")
	//@Consumes(MediaType.APPLICATION_JSON)

    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
	public String userhistory(@QueryParam("post_id") String post_id)
	{
		//String post_id=map.get("post_id");
		String response="";
		ArrayList<JSONObject > ret=new ArrayList<JSONObject >();
		String f="post.title,post.description,user.email_id,tag.tag_name";
		String t="post,post_tag,tag,user";
		String c="where post.post_id=post_tag.post_id and user.user_id=post.user_id and post_tag.tag_id=tag.tag_id and post.post_id="+post_id;
        try {
			ret = DBConnection.select(f,t,c);
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
