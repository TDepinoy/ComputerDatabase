package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.StringUtils;

import utils.JdbcConnection;
import utils.OptionsRequest;

import entites.Company;
import entites.Computer;

public class GestionComputerDaoImpl implements GestionComputerDao {

	
	private final static Logger logger = LoggerFactory.getLogger(GestionComputerDaoImpl.class);
	
	
	private static final String SELECT_ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, cy.id, cy.name from computer c LEFT JOIN company cy ON c.company_id=cy.id ";
	private static final String SELECT_ONE_COMPUTER = "SELECT c.id, c.name, c.introduced, c.discontinued, cy.id, cy.name from computer c LEFT JOIN company cy ON c.company_id=cy.id WHERE c.id=?";
	private static final String INSERT_ONE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	private static final String DELETE_ONE_COMPUTER = "DELETE FROM computer WHERE id=?";
	private static final String UPDATE_ONE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	private static final String COUNT_COMPUTERS = "SELECT COUNT(c.id) as total FROM computer as c";
	private static final String WHERE_FILTER_NAME_STR = " WHERE c.name LIKE ?";
	/**
	 * 1$: colonne sur laquelle s'effectue le tri, 2$: Ordre de tri (asc/desc)
	 */
	private static final String ORDER_BY_LIMIT_STR = " ORDER BY ISNULL (%1$s), %1$s %2$s LIMIT %3$d, %4$d";
	
	
	private static GestionComputerDao dao;

	static {
		dao = new GestionComputerDaoImpl();
	}

	public static GestionComputerDao getInstance() {
		return dao;
	}
	

	@Override
	public void deleteComputer(Connection conn, int id) throws SQLException {
		PreparedStatement pt = null;
		try {
			pt = conn.prepareStatement(DELETE_ONE_COMPUTER);
			pt.setInt(1, id);
			pt.executeUpdate();
		} finally {
			pt.close();
		}
	}

	@Override
	public void insertOrUpdateComputer(Connection conn, Computer c) throws SQLException {		
		if (c.getId() > 0) 
			updateComputer(conn, c);	
		else 
			insertComputer(conn, c);
	}
	
	@Override
	public void insertComputer (Connection conn, Computer c) throws SQLException {
		PreparedStatement pt = null;
		try {
			
			pt = conn.prepareStatement(INSERT_ONE_COMPUTER);
			pt.setString(1, c.getName());		
			
			if (c.getIntroduced() != null)
				pt.setDate(2, new java.sql.Date(c.getIntroduced().getTime()));
			else
				pt.setDate(2, null);
			
			if(c.getDiscontinued() != null)
				pt.setDate(3, new java.sql.Date(c.getDiscontinued().getTime()));
			else
				pt.setDate(3, null);
			
			if (c.getCompany() != null)
				pt.setInt(4, c.getCompany().getId());
			else
				pt.setNull(4, Types.NULL);
					
			pt.executeUpdate();
			
		} finally {
			pt.close();
		}
	}
	
	@Override
	public void updateComputer (Connection conn, Computer c) throws SQLException {
		PreparedStatement pt = null;
		try {
			pt = conn.prepareStatement(UPDATE_ONE_COMPUTER);
			pt.setString(1, c.getName());
			pt.setInt(5, c.getId());			
			
			if (c.getIntroduced() != null)
				pt.setDate(2, new java.sql.Date(c.getIntroduced().getTime()));
			else
				pt.setDate(2, null);
			
			if(c.getDiscontinued() != null)
				pt.setDate(3, new java.sql.Date(c.getDiscontinued().getTime()));
			else
				pt.setDate(3, null);
			
			if (c.getCompany() != null)
				pt.setInt(4, c.getCompany().getId());
			else
				pt.setNull(4, Types.NULL);
					
			pt.executeUpdate();
			
		} finally {
			pt.close();			
		}
	}

	@Override
	public Computer getComputer(Connection conn, int id) throws SQLException {
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

		} finally {
			pt.close();
		}

		return c;
	}

	@Override
	public List<Computer> getComputers(Connection conn, int start, int maxResults, OptionsRequest or) throws SQLException {
		
		PreparedStatement pt = null;
		List<Computer> computers = new ArrayList<Computer>();
		Formatter f = new Formatter ();
		
		try {			
			StringBuilder sb = new StringBuilder (SELECT_ALL_COMPUTERS);
			
			if (!StringUtils.isNullOrEmpty(or.getNameFilter())) 
				sb.append(WHERE_FILTER_NAME_STR);		
			
			f.format(ORDER_BY_LIMIT_STR, or.getOrderC(), or.getOrderW(), start, maxResults);
			sb.append(f.toString());
			
			pt = conn.prepareStatement(sb.toString());
			
			if (!StringUtils.isNullOrEmpty(or.getNameFilter())) 
				pt.setString(1, or.getNameFilter());
			
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
		} finally {
			f.close();
			pt.close();
		}

		return computers;
	}

	@Override
	public int countComputers (Connection conn, String filter) throws SQLException {
		Formatter f = new Formatter ();
		
		PreparedStatement pt = null;
		
		int total = 0;
		
		try {
			
			StringBuilder sb = new StringBuilder (COUNT_COMPUTERS);
			
			if (!StringUtils.isNullOrEmpty(filter)) 
				sb.append(WHERE_FILTER_NAME_STR);
			
			sb.append(f.toString());

			pt = conn.prepareStatement(sb.toString());

			if (!StringUtils.isNullOrEmpty(filter))
				pt.setString(1, filter);
				
			ResultSet res = pt.executeQuery();
			res.first();
			
			total = res.getInt("total");
			
		} finally {
			f.close();
			pt.close();
		}
		
		return total;
	}
}