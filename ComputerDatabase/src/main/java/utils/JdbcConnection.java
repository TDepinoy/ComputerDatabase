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
		//jdbcConnection = new JdbcConnection();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			jdbcConnection = new JdbcConnection();
		} catch (ClassNotFoundException e) {
			logger.error("Impossible de charger le driver JDBC " + e.getMessage());
			System.exit(-1);
		}
	}
	
	private JdbcConnection () {
		this.session = new ThreadLocal<Connection> () {
			
			@Override
			protected Connection initialValue() {
				Connection conn = null;
				try {
					conn = DriverManager.getConnection(URL, USERNAME, PWD);
					conn.setAutoCommit(false);
				} catch (SQLException e) {
					logger.error("Impossible d'établir la connexion " + e.getMessage());
				}
				
				return conn;
			}
			
		};
	}
	
	public static JdbcConnection getInstance () {
		return jdbcConnection;
	}
	
	public Connection getConnection() {	
		return session.get();
	}
	
	public void closeConnection () {
		try {
			if (session.get() != null)
				session.get().close();
		} catch (SQLException e) {
			logger.error("Erreur lors de la fermeture de la connexion" + e.getMessage());
		} finally {
			session.remove();
		}
	}
}
