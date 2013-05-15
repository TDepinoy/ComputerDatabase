package utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;

public class JdbcConnection {
		
	public static final String USERNAME = "root";
	public static final String PWD = "root";
	public static final String URL = "jdbc:mysql://localhost:3306/ComputerDataBase";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Impossible de charger le driver JDBC");
		}
	}
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USERNAME, PWD);
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Impossible d'initialiser la connection");
		}
		return null;
	}
	
	public static void closeConnection (Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Impossible de fermer la connection");
		}
	}
}
