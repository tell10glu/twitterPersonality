package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
	public static  Connection  getDatabaseConnectionPath() throws SQLException{
		return (Connection)DriverManager.getConnection("jdbc:mysql://127.0.0.1/twitter","root","tellioglu");
	}
}
