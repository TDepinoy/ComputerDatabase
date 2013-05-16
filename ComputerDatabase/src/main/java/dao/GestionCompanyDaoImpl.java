package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.JdbcConnection;

import entites.Company;

public class GestionCompanyDaoImpl implements GestionCompanyDao {
	
	public static final String SELECT_ALL_COMPANIES = "SELECT id, name FROM company ORDER BY name";
	public static final String SELECT_ONE_COMPANY = "SELECT id, name FROM company WHERE id=?";
	
	private static GestionCompanyDao dao;

	static {
		dao = new GestionCompanyDaoImpl();
	}

	public static GestionCompanyDao getInstance() {
		return dao;
	}
	
	@Override
	public List<Company> getCompanies() throws SQLException {
		PreparedStatement pt = null;
		List<Company> companies = new ArrayList<Company>();

		try {
			pt = JdbcConnection.getInstance().getConnection().prepareStatement(SELECT_ALL_COMPANIES);
			ResultSet res = pt.executeQuery();

			while (res.next()) {
				Company cy = new Company();;

				cy.setId(res.getInt("id"));
				cy.setName(res.getString("name"));

				companies.add(cy);
			}
		} finally {
			if (pt != null)
				pt.close();
		}

		return companies;
	}
	
	@Override
	public Company getCompany(int id) throws SQLException {
		Company c = new Company();
		PreparedStatement pt = null;

		try {
			pt = JdbcConnection.getInstance().getConnection().prepareStatement(SELECT_ONE_COMPANY);
			pt.setInt(1, id);
			ResultSet res = pt.executeQuery();

			res.first();

			c.setId(res.getInt("id"));
			c.setName(res.getString("name"));
		} finally {
			if (pt != null)
				pt.close();
		}

		return c;
	}
	
}
