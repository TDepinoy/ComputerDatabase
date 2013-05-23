package com.excilys.formation.computerDatabase.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerDatabase.entites.Company;


@Repository
public class GestionCompanyDaoImpl implements GestionCompanyDao {
	
	public static final String SELECT_ALL_COMPANIES = "SELECT id, name FROM company ORDER BY name";
	public static final String SELECT_ONE_COMPANY = "SELECT id, name FROM company WHERE id=?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public GestionCompanyDaoImpl() {}

	
	@Override
	public List<Company> getCompanies() throws DataAccessException {
		return jdbcTemplate.query(SELECT_ALL_COMPANIES, new CompanyRowMapper());
	}
	
	@Override
	public Company getCompany(int id) throws DataAccessException {	
		return jdbcTemplate.query(SELECT_ONE_COMPANY, new Object[] {id}, new CompanyRowMapper()).get(0);
	}
	
	public class CompanyRowMapper implements RowMapper<Company> {
		
	  @Override
	  public Company mapRow(ResultSet rs, int line) throws SQLException {
		  Company company = null;
		  company = new Company(rs.getInt("id"),rs.getString("name"));
		  return company;
	  }
	}
	
}
