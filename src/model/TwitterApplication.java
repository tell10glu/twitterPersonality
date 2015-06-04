package model;

import helper.DatabaseHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class TwitterApplication {
	private int id;
	private boolean available = true;
	public boolean isAvailable(){
		return available;
	}
	private Date lastUsedTime;
	public Date getLastUsedTime(){
		return lastUsedTime;
	}
	public void setLastUsedTime(Date lastUsedTime){
		this.lastUsedTime = lastUsedTime;
	}
	public int getId() {
		return id;
	}
	public String getConsumerKey() {
		return ConsumerKey;
	}
	public String getConsumerSecret() {
		return ConsumerSecret;
	}
	public String getAccessToken() {
		return AccessToken;
	}
	public String getAccessTokenSecret() {
		return AccessTokenSecret;
	}
	
	private String ConsumerKey,ConsumerSecret,AccessToken,AccessTokenSecret;
	public TwitterApplication(int id, String consumerKey,
			String consumerSecret, String accessToken, String accessTokenSecret) {
		super();
		this.id = id;
		ConsumerKey = consumerKey;
		ConsumerSecret = consumerSecret;
		AccessToken = accessToken;
		AccessTokenSecret = accessTokenSecret;
	}
	
	public static ArrayList<TwitterApplication> getTwitterApplications(){
		ArrayList<TwitterApplication> list = new ArrayList<TwitterApplication>();
		Connection con  = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DatabaseHelper.getDatabaseConnectionPath();
			Statement st = con.createStatement();
			ResultSet set = st.executeQuery("Select * from TwitterApplications");
			while(set.next()){
				list.add(new TwitterApplication(set.getInt(1),set.getString(2),set.getString(3),set.getString(4),set.getString(5))) ;
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
		return list;
	}
	public void setAvailable(boolean b) {
		this.available = b;
		
	}
}
