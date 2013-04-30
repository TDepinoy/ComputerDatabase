package utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;

public class JdbcConnection {
		
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Impossible de charger le driver JDBC");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(String url, String username, String pwd) {
		try {
			return DriverManager.getConnection(url, username, pwd);
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Impossible d'initialiser la connection");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeConnection (Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Impossible de fermer la connection");
			e.printStackTrace();
		}
	}
}
