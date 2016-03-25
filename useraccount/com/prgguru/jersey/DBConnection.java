package com.prgguru.jersey;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.jdbc.ResultSetMetaData;
 
public class DBConnection {
    /**
     * Method to create DB Connection
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName(Constants.dbClass);
            con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
        } catch (Exception e) {
        	System.out.println(e);
            throw e;
        } finally {
            return con;
        }
    }
    
    public static int getCount(String table_name,String field_name,String clause) throws SQLException{
    	int count=0;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            System.out.println("after statement");
            String query= "select count("+field_name+") from "+ table_name + " " + clause + " ;";
            System.out.println(query);
            ResultSet records = stmt.executeQuery(query);
            //System.out.println(records);
            //When record is successfully inserted
            records.next();
            count=records.getInt(1);
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
    	
    	return count;
    }
    
    /**
     * Method to insert uname and pwd in DB
     * 
     * @param name
     * @param uname
     * @param pwd
     * @return
     * @throws SQLException
     * @throws Exception
     */
    ///old_code an be replaced///
    public static boolean insertUser(String name, String uname, String pwd,String salt) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            System.out.println("after statement");
            String query = "INSERT into user(username, email_id, password,cook) values('"+name+ "',"+"'"
                    + uname + "','" + pwd + "','" + salt + "')";
            System.out.println(query);
            int records = stmt.executeUpdate(query);
            System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
    
    //updating otpstring
    public static boolean updateUser(String field_name,String field_value,boolean flag,String email) throws SQLException, Exception {
        boolean updateStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query;
            //System.out.println("after statement");
            if(flag == true){
            	boolean value=true;
            	query = "UPDATE user SET "+ field_name+ " = "+ value +" where email_id = \"" + email+"\" ;";
            }
            else
            {
            	query = "UPDATE user SET "+ field_name+ " = "+ field_value +" where email_id = \"" + email+"\" ; ";
            }
            System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                updateStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return updateStatus;
    }
 ////end_of_old_code  
    public static ArrayList< JSONObject > select(String field_name,String table_name,String clause) throws SQLException, Exception {
        Connection dbConn = null;
        ArrayList<String> colNaam=new ArrayList<String>();
        ArrayList<String> coltype=new ArrayList<String>();
       // HashMap<String,String> row=new HashMap<String,String>();
        ArrayList< JSONObject > ret=new ArrayList<JSONObject >();
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query;
            //System.out.println("after statement");
           	query = "Select "+field_name+" from "+table_name+" "+clause+";";
            System.out.println(query);
            ResultSet records = stmt.executeQuery(query);
            //System.out.println(records);
            //When record is successfully inserted
            java.sql.ResultSetMetaData rsmd = records.getMetaData();
            int columnCount = rsmd.getColumnCount();
           // System.out.println(columnCount);
            for (int i = 1; i < columnCount + 1; i++ ) {
            	  colNaam.add(rsmd.getColumnName(i));
            	//  	System.out.println(colNaam.get(i-1));
            	  coltype.add(rsmd.getColumnTypeName(i));
            	// 	System.out.println(coltype.get(i-1));
            	}
           /* System.out.println(colNaam.size());
            for(int i=0;i<colNaam.size();i++){
            	System.out.print(colNaam.get(i)+" ");
            }
            System.out.println(coltype.size());
            for(int i=0;i<coltype.size();i++){
            	System.out.print(coltype.get(i)+" ");
            }*/
            while(records.next())
            {
            //	System.out.println("how many?");
            	
            	ArrayList<String> row=new ArrayList<String>();
            	
            	for (int i = 0; i < columnCount ; i++ ) {
            	
              	  	if(coltype.get(i).equals("INT")){
              	  		row.add(Integer.toString(records.getInt(i+1)));
              	  	//	 System.out.println(records.getInt(i+1));
              	  	}
              	  	if(coltype.get(i).equals("TINYINT")){
              	  		row.add(Boolean.toString(records.getBoolean(i+1)));
              	  		//	System.out.println(records.getBoolean(i+1));
              	  	}
              	  	if(coltype.get(i).equals("VARCHAR")){
              	  		row.add((records.getString(i+1)));
              	  	//	 System.out.println(records.getString(i+1));
              	  	}
              	  	if(coltype.get(i).equals("TIMESTAMP")){
              	  		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
              	  		//String string  = (records.getTimestamp(i+1).toString());
              	  		row.add(""+ records.getTimestamp(i+1));
              	  		//	System.out.println(records.getString(i+1));
              	  	}
            	}
            /*	System.out.println(row.size());
                for(int i=0;i<row.size();i++){
                	System.out.print(row.get(i)+" ");
                }*/
            	ret.add(Utitlity.constructJSON(colNaam,row));
           }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
           // e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
       // System.out.println("sdfsd");
        //System.out.println(ret);
        return ret;
    }
    public static boolean insert(String field_names, String table_name, String values) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            System.out.println("after statement");
            String query = "INSERT into "+table_name+"("+field_names+")values("+values+");";
            System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
    public static boolean update(String table_name,String field_name,String value,String clause) throws SQLException, Exception {
        boolean updateStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query;
            //System.out.println("after statement");
            query = "UPDATE "+table_name+" SET "+ field_name+ " = "+ value+" "+ clause +";";
            System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                updateStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return updateStatus;
    }
    public static boolean Isverified(String uid,String cooky){
    	ArrayList<JSONObject > newret=new ArrayList<JSONObject >();
        try {
			newret = DBConnection.select("email_id , cook","user","where user_id="+uid);
			}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(newret+"Isverifird");
        String salt = "",email_id="",cookies="";
		try {
			 salt = newret.get(0).getString("cook");
			 email_id=newret.get(0).getString("email_id");
			 cookies = encrypt.getSecurePassword(email_id,salt);
			if(!(cookies.equals(cooky)))
			{
				return false;
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
    }
    public static boolean delete(String table_name,String clause) throws SQLException, Exception {
        boolean updateStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query;
            //System.out.println("after statement");
            query = "DELETE FROM "+table_name+" "+clause +";";
            System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                updateStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return updateStatus;
    }
   
    
}