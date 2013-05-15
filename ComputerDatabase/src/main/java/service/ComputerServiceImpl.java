package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.JdbcConnection;
import utils.OptionsRequest;
import utils.Page;

import dao.GestionCompanyDaoImpl;
import dao.GestionCompanyDao;
import dao.GestionComputerDao;
import dao.GestionComputerDaoImpl;
import entites.Company;
import entites.Computer;

public class ComputerServiceImpl implements ComputerService {
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	
	private static ComputerService service;
	
	private GestionComputerDao dao;
	private GestionCompanyDao daoCy;
	
	static {
		service = new ComputerServiceImpl();
	}
	
	private ComputerServiceImpl () {
		dao = GestionComputerDaoImpl.getInstance();
		daoCy = GestionCompanyDaoImpl.getInstance();
	}
	
	public static ComputerService getInstance() {
		return service;
	}
	
	@Override
	public Computer getComputer (int id) {
		Connection conn = JdbcConnection.getConnection();
		Computer c = null;
		
		try {
			c = dao.getComputer(conn, id);
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		
		JdbcConnection.closeConnection(conn);
		return c;
	}
	
	@Override
	public List<Computer> getComputers(int start, int maxResults, OptionsRequest or) {
		Connection conn = JdbcConnection.getConnection();
		List<Computer> computers = null;
		
		try {
			computers = dao.getComputers(conn, start, maxResults, or);
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		
		JdbcConnection.closeConnection(conn);
		return computers;
	}
	
	@Override
	public int countComputers(String filter) {
		Connection conn = JdbcConnection.getConnection();
		int totalResults = 0;
		
		try {
			totalResults = dao.countComputers(conn, filter);
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		
		JdbcConnection.closeConnection(conn);
		return totalResults;
	}

	@Override
	public void insertOrUpdateComputer (Computer c) {
		Connection conn = JdbcConnection.getConnection();
		
		try {
			dao.insertOrUpdateComputer(conn, c);
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		
		JdbcConnection.closeConnection(conn);	
	}
	
	@Override
	public void deleteComputer (int id) {
		Connection conn = JdbcConnection.getConnection();
		
		try {
			dao.deleteComputer(conn, id);
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		JdbcConnection.closeConnection(conn);	
	}
	
	@Override
	public List<Company> getCompanies () {
		Connection conn = JdbcConnection.getConnection();
		List<Company> companies = null;
		
		try {
			companies = daoCy.getCompanies(conn);
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		
		JdbcConnection.closeConnection(conn);
		return companies;
	}
	
	@Override
	public Company getCompany (int id) {
		Connection conn = JdbcConnection.getConnection();
		Company c = null;
		
		try {
			c = daoCy.getCompany(conn, id);
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		JdbcConnection.closeConnection(conn);
		return c;
	}
	
	@Override
	public Page createPage (int numPage, int maxResults, OptionsRequest or) {
		int totalResults = countComputers(or.getNameFilter());
		
		if (numPage < 0 || (numPage > 0 && (totalResults - numPage * maxResults < 0)))
			numPage = 0;
		
		return new Page (numPage, maxResults, totalResults, getComputers(numPage * maxResults, maxResults, or));
	}
	
}
