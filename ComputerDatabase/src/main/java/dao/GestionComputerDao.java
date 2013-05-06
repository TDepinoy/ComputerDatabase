package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.StringUtils;

import utils.JdbcConnection;
import utils.OptionsRequest;

import entites.Company;
import entites.Computer;

public class GestionComputerDao {

	private static final String SELECT_ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, cy.id, cy.name from computer c LEFT JOIN company cy ON c.company_id=cy.id ";
	private static final String SELECT_ONE_COMPUTER = "SELECT c.id, c.name, c.introduced, c.discontinued, cy.id, cy.name from computer c LEFT JOIN company cy ON c.company_id=cy.id WHERE c.id=?";
	private static final String INSERT_ONE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	private static final String DELETE_ONE_COMPUTER = "DELETE FROM computer WHERE id=?";
	private static final String UPDATE_ONE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	private static final String COUNT_COMPUTERS = "SELECT COUNT(c.id) as total FROM computer as c";
	private static final String WHERE_FILTER_NAME_STR = " WHERE c.name LIKE %s";
	private static final String ORDER_BY_LIMIT_STR = " ORDER BY ISNULL (%1$s), %1$s %2$s LIMIT %3$d, %4$d";
	
	
	private static GestionComputerDao dao;

	static {
		dao = new GestionComputerDao();
	}

	public static GestionComputerDao getInstance() {
		return dao;
	}
	
	public void deleteComputer(int id) {
		Connection conn = JdbcConnection.getConnection();
		PreparedStatement pt = null;
		try {
			pt = conn.prepareStatement(DELETE_ONE_COMPUTER);
			pt.setInt(1, id);
			pt.executeUpdate();
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
	}

	public void insertOrUpdateComputer(Computer c) {
		Connection conn = JdbcConnection.getConnection();
		PreparedStatement pt = null;
		try {
			if (c.getId() > 0) {
				pt = conn.prepareStatement(UPDATE_ONE_COMPUTER);
				pt.setInt(5, c.getId());
			}
			else {
				pt = conn.prepareStatement(INSERT_ONE_COMPUTER);
			}
			pt.setString(1, c.getName());
			
			if (c.getIntroduced() != null)
				pt.setDate(2, new java.sql.Date(c.getIntroduced().getTime()));
			else
				pt.setDate(2, null);
			
			if(c.getDiscontinued() != null)
				pt.setDate(3, new java.sql.Date(c.getDiscontinued().getTime()));
			else
				pt.setDate(3, null);
			
			pt.setInt(4, c.getCompany().getId());
			
			pt.executeUpdate();
			
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
	}

	public Computer getComputer(int id) {
		Connection conn = JdbcConnection.getConnection();
		Computer c = new Computer();
		PreparedStatement pt = null;

		try {
			pt = conn.prepareStatement(SELECT_ONE_COMPUTER);
			pt.setInt(1, id);
			ResultSet res = pt.executeQuery();

			res.first();

			Company cy = new Company();
			c.setId(res.getInt("c.id"));
			c.setName(res.getString("c.name"));
			c.setIntroduced(res.getDate("c.introduced"));
			c.setDiscontinued(res.getDate("c.discontinued"));

			cy.setId(res.getInt("cy.id"));
			cy.setName(res.getString("cy.name"));

			c.setCompany(cy);

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

	public List<Computer> getComputers(int start, int maxResults, OptionsRequest or) {
		Connection conn = JdbcConnection.getConnection();
		PreparedStatement pt = null;
		List<Computer> computers = new ArrayList<Computer>();
		Formatter f = new Formatter ();
		
		try {			
			StringBuilder sb = new StringBuilder (SELECT_ALL_COMPUTERS);
			
			if (!StringUtils.isNullOrEmpty(or.getNameFilter())) 
				f.format(WHERE_FILTER_NAME_STR, or.getNameFilter());		
			
			f.format(ORDER_BY_LIMIT_STR, or.getOrderC().toString(), or.getOrderW().toString(), start, maxResults);
			sb.append(f.toString());
			
			System.out.println(sb.toString());
			pt = conn.prepareStatement(sb.toString());			
			
			ResultSet res = pt.executeQuery();

			while (res.next()) {
				Computer c = new Computer();
				Company cy = new Company();
				c.setId(res.getInt("c.id"));
				c.setName(res.getString("c.name"));
				c.setIntroduced(res.getDate("c.introduced"));
				c.setDiscontinued(res.getDate("c.discontinued"));

				cy.setId(res.getInt("cy.id"));
				cy.setName(res.getString("cy.name"));

				c.setCompany(cy);

				computers.add(c);
			}
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Erreur de syntaxe SQL");
			e.printStackTrace();
		} finally {
			JdbcConnection.closeConnection(conn);
			f.close();
			
			try {
				pt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return computers;
	}
	
	public int countComputers (String filter) {
		Formatter f = new Formatter ();
		
		Connection conn = JdbcConnection.getConnection();
		PreparedStatement pt = null;
		
		int total = 0;
		
		try {
			
			StringBuilder sb = new StringBuilder (COUNT_COMPUTERS);
			
			if (!StringUtils.isNullOrEmpty(filter)) 
				f.format(WHERE_FILTER_NAME_STR, new StringBuilder().append("'%").append(filter).append("%'"));
			
			sb.append(f.toString());

			pt = conn.prepareStatement(sb.toString());

			ResultSet res = pt.executeQuery();
			res.first();
			
			total = res.getInt("total");
			
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Erreur de syntaxe SQL");
			e.printStackTrace();
		} finally {
			JdbcConnection.closeConnection(conn);

			try {
				f.close();
				pt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return total;
	}
}
