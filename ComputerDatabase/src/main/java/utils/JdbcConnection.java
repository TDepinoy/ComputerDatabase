package utils;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcConnection {
		
	private static final Logger logger = LoggerFactory.getLogger(JdbcConnection.class);
	
	public static final String USERNAME = "root";
	public static final String PWD = "root";
	public static final String URL = "jdbc:mysql://localhost:3306/ComputerDataBase";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("Impossible de charger le driver JDBC " + e.getMessage());
		}
	}
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USERNAME, PWD);
		} catch (SQLException e) {
			logger.error("Impossible d'établir une connexion" + e.getMessage());
		}
		return null;
	}
	
	public static void closeConnection (Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error("Erreur lors de la fermeture de la connexion" + e.getMessage());
		}
	}
}
