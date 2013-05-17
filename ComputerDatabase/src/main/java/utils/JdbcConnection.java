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
	
	private static JdbcConnection jdbcConnection;
	
	public ThreadLocal<Connection> session;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			jdbcConnection = new JdbcConnection();
		} catch (ClassNotFoundException e) {
			logger.error("Impossible de charger le driver JDBC " + e.getMessage());
			System.exit(-1);
		}
	}
	
	private JdbcConnection () {
		session = new ThreadLocal<Connection> ();
	}
	
	public static JdbcConnection getInstance () {
		return jdbcConnection;
	}
	
	public Connection getConnection() {

		if (session.get() == null) {
			try {
				Connection conn = DriverManager.getConnection(URL, USERNAME, PWD);
				conn.setAutoCommit(false);
				session.set(conn);
			} catch (SQLException e) {
				logger.error("Impossible d'établir la connexion " + e.getMessage());
			}
		}
		
		return session.get();
	}
	
	public void closeConnection () {
		try {
			if (session.get() != null)
				session.get().close();
		} catch (SQLException e) {
			logger.error("Erreur lors de la fermeture de la connexion" + e.getMessage());
		}
		session.remove();
	}
}
