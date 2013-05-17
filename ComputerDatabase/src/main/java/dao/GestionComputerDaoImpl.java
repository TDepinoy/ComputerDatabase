package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import utils.JdbcConnection;
import utils.OptionsRequest;

import com.mysql.jdbc.StringUtils;

import entites.Company;
import entites.Computer;

public class GestionComputerDaoImpl implements GestionComputerDao {
	
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
	public int deleteComputer(int id) throws SQLException {
		PreparedStatement pt = null;
		int result = 0;
		try {
			pt = JdbcConnection.getInstance().getConnection().prepareStatement(DELETE_ONE_COMPUTER);
			pt.setInt(1, id);
			result = pt.executeUpdate();
		} finally {
			if (pt != null)
				pt.close();
		}
		
		return result;
	}
	
	@Override
	public int insertComputer (Computer c) throws SQLException {
		PreparedStatement pt = null;
		int result = 0;
		try {
			
			pt = JdbcConnection.getInstance().getConnection().prepareStatement(INSERT_ONE_COMPUTER);
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
					
			result = pt.executeUpdate();
			
		} finally {
			if (pt != null)
				pt.close();
		}
		
		return result;
	}
	
	@Override
	public int updateComputer (Computer c) throws SQLException {
		PreparedStatement pt = null;
		int result = 0;
		try {
			pt = JdbcConnection.getInstance().getConnection().prepareStatement(UPDATE_ONE_COMPUTER);
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
					
			result = pt.executeUpdate();
			
		} finally {
			if (pt != null)
				pt.close();		
		}
		
		return result;
	}

	@Override
	public Computer getComputer(int id) throws SQLException {
		Computer c = null;
		PreparedStatement pt = null;

		try {
			pt = JdbcConnection.getInstance().getConnection().prepareStatement(SELECT_ONE_COMPUTER);
			pt.setInt(1, id);
			ResultSet res = pt.executeQuery();

			if (res.first()) {
				c = new Computer();

				c.setId(res.getInt("c.id"));
				c.setName(res.getString("c.name"));
				c.setIntroduced(res.getDate("c.introduced"));
				c.setDiscontinued(res.getDate("c.discontinued"));
	
				if (res.getInt("cy.id") != 0) {
					Company cy = new Company();
					cy.setId(res.getInt("cy.id"));
					cy.setName(res.getString("cy.name"));				
					c.setCompany(cy);
				}
			}

		} finally {
			if (pt != null)
				pt.close();
		}

		return c;
	}

	@Override
	public List<Computer> getComputers(int start, int maxResults, OptionsRequest or) throws SQLException {
		
		PreparedStatement pt = null;
		List<Computer> computers = new ArrayList<Computer>();
		Formatter f = new Formatter ();
		
		try {			
			StringBuilder sb = new StringBuilder (SELECT_ALL_COMPUTERS);
			
			if (!StringUtils.isNullOrEmpty(or.getNameFilter())) 
				sb.append(WHERE_FILTER_NAME_STR);		
			
			f.format(ORDER_BY_LIMIT_STR, or.getOrderC(), or.getOrderW(), start, maxResults);
			sb.append(f.toString());
			
			pt = JdbcConnection.getInstance().getConnection().prepareStatement(sb.toString());
			
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
			if (f != null)
				f.close();
			if (pt != null)
				pt.close();
		}

		return computers;
	}

	@Override
	public int countComputers (String filter) throws SQLException {
		Formatter f = new Formatter ();
		
		PreparedStatement pt = null;
		
		int total = 0;
		
		try {
			
			StringBuilder sb = new StringBuilder (COUNT_COMPUTERS);
			
			if (!StringUtils.isNullOrEmpty(filter)) 
				sb.append(WHERE_FILTER_NAME_STR);
			
			sb.append(f.toString());

			pt = JdbcConnection.getInstance().getConnection().prepareStatement(sb.toString());

			if (!StringUtils.isNullOrEmpty(filter))
				pt.setString(1, filter);
				
			ResultSet res = pt.executeQuery();
			res.first();
			
			total = res.getInt("total");
			
		} finally {
			if (f != null)
				f.close();
			if (pt != null)
				pt.close();
		}
		
		return total;
	}
}
