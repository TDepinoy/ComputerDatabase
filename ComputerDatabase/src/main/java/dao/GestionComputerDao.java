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

public class GestionComputerDao {

	public static final String SELECT_ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, cy.id, cy.name from computer c LEFT JOIN company cy ON c.company_id=cy.id LIMIT ?,?";
	public static final String SELECT_ONE_COMPUTER = "SELECT c.id, c.name, c.introduced, c.discontinued, cy.id, cy.name from computer c LEFT JOIN company cy ON c.company_id=cy.id WHERE c.id=?";
	public static final String INSERT_ONE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	public static final String DELETE_ONE_COMPUTER = "DELETE FROM computer WHERE id=?";
	public static final String UPDATE_ONE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	public static final String COUNT_COMPUTERS = "SELECT COUNT(id) as total FROM computer";

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
				pt.setString(1, c.getName());
				pt.setDate(2, new java.sql.Date(c.getIntroduced().getTime()));
				pt.setDate(3, new java.sql.Date(c.getDiscontinued().getTime()));
				pt.setInt(4, c.getCompany().getId());
				pt.setInt(5, c.getId());
				pt.executeUpdate();
			}
			else {
				pt = conn.prepareStatement(INSERT_ONE_COMPUTER);
				pt.setString(1, c.getName());
				pt.setDate(2, new java.sql.Date(c.getIntroduced().getTime()));
				pt.setDate(3, new java.sql.Date(c.getDiscontinued().getTime()));
				pt.setInt(4, c.getCompany().getId());
				
				pt.executeUpdate();
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

	public List<Computer> getComputers(int start, int maxResults) {
		Connection conn = JdbcConnection.getConnection();
		PreparedStatement pt = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			pt = conn.prepareStatement(SELECT_ALL_COMPUTERS);
			pt.setInt(1, start*maxResults);
			pt.setInt(2, maxResults);
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

			try {
				pt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return computers;
	}
	
	public int countComputers () {
		Connection conn = JdbcConnection.getConnection();
		PreparedStatement pt = null;
		
		int total = 0;
		
		try {
			pt = conn.prepareStatement(COUNT_COMPUTERS);
			ResultSet res = pt.executeQuery();
			res.first();
			
			total = res.getInt("total");
			
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
		
		return total;
	}
}
