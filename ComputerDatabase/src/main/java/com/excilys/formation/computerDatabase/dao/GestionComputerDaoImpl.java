package com.excilys.formation.computerDatabase.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerDatabase.entites.Company;
import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.utils.OptionsRequest;
import com.mysql.jdbc.StringUtils;

@Repository
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
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public GestionComputerDaoImpl () {}

	
	@Override
	public int deleteComputer(int id) throws SQLException {
		return jdbcTemplate.update(DELETE_ONE_COMPUTER, new Object [] {id});
	}
	
	@Override
	public int insertComputer (Computer c) throws SQLException {	
		return jdbcTemplate.update(INSERT_ONE_COMPUTER, new Object [] {c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompany().getId()});
	}
	
	@Override
	public int updateComputer (Computer c) throws SQLException {
		return jdbcTemplate.update(UPDATE_ONE_COMPUTER, new Object [] {c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompany().getId(), c.getId()});
	}

	@Override
	public Computer getComputer(int id) throws SQLException {
		List<Computer> result = jdbcTemplate.query(SELECT_ONE_COMPUTER, new Object [] {id}, new ComputerRowMapper());
		
		if (!result.isEmpty())
			return result.get(0);
		
		return null;
	}

	@Override
	public List<Computer> getComputers(int start, int maxResults, OptionsRequest or) throws SQLException {	
		Formatter f = new Formatter ();
		StringBuilder query = new StringBuilder (SELECT_ALL_COMPUTERS);
		List<Computer> computers;
		
		if (!StringUtils.isNullOrEmpty(or.getNameFilter())) 
			query.append(WHERE_FILTER_NAME_STR);
		
		f.format(ORDER_BY_LIMIT_STR, or.getOrderC(), or.getOrderW(), start, maxResults);
		query.append(f.toString());
		
		if (!StringUtils.isNullOrEmpty(or.getNameFilter())) 
			computers = jdbcTemplate.query(query.toString(), new Object[] {or.getNameFilter()}, new ComputerRowMapper());
		else
			computers = jdbcTemplate.query(query.toString(), new ComputerRowMapper());
		
		if(f != null)
			f.close();
		
		return computers;
	}

	@Override
	public int countComputers (String filter) throws SQLException {
			
		int total = 0;			
		StringBuilder query = new StringBuilder (COUNT_COMPUTERS);
		
		if (!StringUtils.isNullOrEmpty(filter)) 
			query.append(WHERE_FILTER_NAME_STR);

		if (!StringUtils.isNullOrEmpty(filter))
			total = jdbcTemplate.query(query.toString(), new Object[] {filter}, new ComputerCountRowMapper()).get(0);
		else	
			total = jdbcTemplate.query(query.toString(), new ComputerCountRowMapper()).get(0);
			
		return total;
	}
	
	/**
	 * Mapper computer requests
	 * @author tdepinoy
	 *
	 */
	private class ComputerRowMapper implements RowMapper<Computer> {
	
	  @Override
	  public Computer mapRow(ResultSet rs, int line) throws SQLException {
		  Computer computer = null;
		  computer = new Computer(rs.getInt("c.id"),rs.getString("c.name"), rs.getDate("c.introduced"), rs.getDate("c.discontinued"), null);
		  
		  if (rs.getInt("cy.id") != 0)
			  computer.setCompany(new Company(rs.getInt("cy.id"), rs.getString("cy.name")));
		  
		  return computer;
	  }
	}
	
	/**
	 * Mapper computer count request
	 * @author tdepinoy
	 *
	 */
	private class ComputerCountRowMapper implements RowMapper<Integer> {

		@Override
		public Integer mapRow(ResultSet rs, int line) throws SQLException {
			rs.first();
			return rs.getInt("total");
		}
	}
}
