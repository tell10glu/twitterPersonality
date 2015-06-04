package model;

import helper.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
	private int id;
	private String userName;
	public User(int id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public static void setUserTweetsReaded(String userName){
		Connection con  = null;
		String query = null;
		try {
			con = DatabaseHelper.getDatabaseConnectionPath();
			query = "Update User set isTweetReaded = ? where userName = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setBoolean(1, true);
			st.setString(2, userName);
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public static boolean isUserTweetReaded(String userName){
		Connection con  = null;
		boolean tweetreaded = false;
		String query = null;
		try {
			
			con = DatabaseHelper.getDatabaseConnectionPath();
			query = "Select isTweetReaded from User where userName = ? ";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, userName);
			ResultSet set = st.executeQuery();
			if(set.next()){
				tweetreaded = set.getBoolean(1);
				
			}else{
				tweetreaded = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return tweetreaded;
	}
	public static boolean isUserExists(String userName){
		Connection con  = null;
		boolean exists = false;
		String query = null;
		try {
			con = DatabaseHelper.getDatabaseConnectionPath();
			query = "Select * from User where userName = ? ";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, userName);
			ResultSet set = st.executeQuery();
			if(set.next()){
				exists = true;
			}else{
				exists = false;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return exists;
	}
	public static void addNewUser(String userName){
		Connection con  = null;
		String query = null;
		try {
			con = DatabaseHelper.getDatabaseConnectionPath();
			query = " insert into User(userName) VALUES(?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, userName);
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
