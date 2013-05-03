package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.JdbcConnection;
import entites.Company;
import entites.Computer;

public class GestionCompanyDao {
	public static final String SELECT_ALL_COMPANIES = "SELECT id, name FROM company";
	public static final String SELECT_ONE_COMPANY = "SELECT id, name FROM company WHERE id=?";
	private static GestionCompanyDao dao;

	static {
		dao = new GestionCompanyDao();
	}

	public static GestionCompanyDao getInstance() {
		return dao;
	}
	
	public List<Company> getCompanies() {
		Connection conn = JdbcConnection.getConnection();
		PreparedStatement pt = null;
		List<Company> companies = new ArrayList<Company>();

		try {
			pt = conn.prepareStatement(SELECT_ALL_COMPANIES);
			ResultSet res = pt.executeQuery();

			while (res.next()) {
				Company cy = new Company();;

				cy.setId(res.getInt("id"));
				cy.setName(res.getString("name"));

				companies.add(cy);
			}
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Erreur de syntaxe SQL");
			e.printStackTrace();
		} finally {
			JdbcConnection.closeConnection(conn);

			try {
				pt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return companies;
	}
	
	public Company getCompany(int id) {
		Connection conn = JdbcConnection.getConnection();
		Company c = new Company();
		PreparedStatement pt = null;

		try {
			pt = conn.prepareStatement(SELECT_ONE_COMPANY);
			pt.setInt(1, id);
			ResultSet res = pt.executeQuery();

			res.first();

			c.setId(res.getInt("id"));
			c.setName(res.getString("name"));

		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Erreur de syntaxe SQL");
			e.printStackTrace();
		} finally {
			JdbcConnection.closeConnection(conn);

			try {
				pt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return c;
	}
	
}
