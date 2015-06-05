package model;

import helper.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import com.mysql.jdbc.Statement;

/**
 * Twitleri kaydeden model
 * @author abdullahtellioglu
 *
 */
public class Tweets {
	private int id;
	private String tweet;
	private boolean calculated;
	private Date calculationDate;
	public int getId() {
		return id;
	}
	public String getTweet() {
		return tweet;
	}
	public boolean isCalculated() {
		return calculated;
	}
	public Date getCalculationDate() {
		return calculationDate;
	}
	/**
	 * kullanıcının attığı tweeti veritabanına kaydeder.
	 * Kullanıcı ekli değilse kullanıcıyı oluşturur.
	 * Tweetler eklenir .
	 * KullanıcıTweet tablosuna ekleme yapılır
	 * @param tweet kullanıcının attığı tweet
	 * @param userName kullanıcı adı
	 */
	public static void addTweet(String tweet,String userName){
		Connection con  = null;
		String query = null;
		try {
			con = DatabaseHelper.getDatabaseConnectionPath();
			query = " insert into Tweets(tweet) VALUES(?)";
			PreparedStatement st = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			st.setString(1, tweet);
			long tweetid = st.executeUpdate();
			if(tweetid==0){
				// hata cozumu yap
			}else{
				// returns last id !!
				ResultSet generatedKeys = st.getGeneratedKeys(); 
		        if (generatedKeys.next()) {
		        	tweetid = generatedKeys.getLong(1);
		        }
			}
			st.clearBatch();
			st.clearParameters();
			query = "select id from User where userName = ?";
			st = con.prepareStatement(query);
			st.setString(1, userName);
			ResultSet set = st.executeQuery();
			int userId = 0;
			if(set.next()){
				userId = set.getInt(1);
			}
			if(userId ==0){
				User.addNewUser(userName);
				set = st.executeQuery();
				if(set.next()){
					userId = set.getInt(1);
				}
				// kullanici kayit edilmemis kullanici kayit yap
			}
			st.clearBatch();
			st.clearParameters();
			st = con.prepareStatement("insert into UserTweets(userid,tweetId) VALUES(?,?)");
			st.setInt(1, userId);
			st.setLong(2, tweetid);
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
	public static boolean isTweetOK(String tweet){
		boolean isok = true;
		if(tweet.startsWith("RT @")){
			isok = false;
		}
		return isok;
	}
}
