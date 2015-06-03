package helper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
public class Log {
	private static final  String ACTION_INFO = "info";
	private static final String ACTION_ERROR = "error";
	private static final String ACTION_SYSTEM_ERROR = "systemerror";
	private static final String ACTION_USER_TWEET = "usertweet";
	private static final String ACTION_NEW_USER = "newuser";
	private static final String ACTION_APPLICATON_EXCEED = "limitexceeded";
	private static void wDb(String cikti,String ACTION){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DatabaseHelper.getDatabaseConnectionPath();
			String query = "insert into Log(Tarih,Saat,AksiyonAdi,Cikti) VALUES (?,?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setDate(1, new Date(new java.util.Date().getTime()));
			st.setTime(2, new Time(new java.util.Date().getTime()));
			st.setString(3,ACTION);
			st.setString(4, cikti);
			st.executeUpdate();
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		} 
		catch(Exception e){
			Log.systemError(e.getMessage().toString());
			e.getMessage();
		}
		finally{
			try {
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	public static void newUser(String cikti){
		wDb(cikti,ACTION_NEW_USER);
	}
	public static void systemError(String cikti){
		wDb(cikti,ACTION_SYSTEM_ERROR);
		
	}
	public static void userTweet(String userName){
		wDb(userName,ACTION_USER_TWEET);
	}
	public static void i(String cikti){
		wDb(cikti, ACTION_INFO);
		
	}
	public static void applicationLimit(String cikti){
		wDb(cikti, ACTION_APPLICATON_EXCEED);
	}
	public static void e(String cikti){
		wDb(cikti,ACTION_ERROR);
	}
}
