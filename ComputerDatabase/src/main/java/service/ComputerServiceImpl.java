package service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.JdbcConnection;
import utils.OptionsRequest;

import dao.GestionCompanyDaoImpl;
import dao.GestionCompanyDao;
import dao.GestionComputerDao;
import dao.GestionComputerDaoImpl;
import entites.Company;
import entites.Computer;
import entites.Page;
import exceptions.DataAccessException;

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
	public Computer getComputer (int id) throws DataAccessException {	
		Computer c = null;
		
		try {
			c = dao.getComputer(id);
			
			if (c == null)
				throw new DataAccessException("Computer inexistant");
			
		} catch (SQLException e) {
			logger.warn("Erreur lors de la récupération du computer " + e.getMessage());
		}
		
		JdbcConnection.getInstance().closeConnection();
		return c;
	}
	
	@Override
	public List<Computer> getComputers(int start, int maxResults, OptionsRequest or) {
		List<Computer> computers = null;
		
		try {
			computers = dao.getComputers(start, maxResults, or);
		} catch (SQLException e) {
			logger.warn("Erreur lors de l'obtention de la liste des computers " + e.getMessage());
		}
		
		JdbcConnection.getInstance().closeConnection();
		return computers;
	}
	
	@Override
	public int countComputers(String filter) {
		int totalResults = 0;
		
		try {
			totalResults = dao.countComputers(filter);
		} catch (SQLException e) {
			logger.warn("Erreur lors du count computers" + e.getMessage());
		}
		
		JdbcConnection.getInstance().closeConnection();
		return totalResults;
	}

	@Override
	public void insertOrUpdateComputer (Computer c) throws DataAccessException {
		
		int result = 0;
		
		try {
			if (c.getId() > 0)
				result = dao.updateComputer(c);
			else 
				result = dao.insertComputer(c);
			
			if (result == 0)
				throw new DataAccessException("Error lors de l'insert/update");
			
			JdbcConnection.getInstance().getConnection().commit();
			
		} catch (SQLException e) {
			logger.warn("Erreur lors de l'insertion ou la mise à jour d'un computer " + e.getMessage());
			
			try {
				JdbcConnection.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
		} finally {
			JdbcConnection.getInstance().closeConnection();
		}
	}
	
	@Override
	public void deleteComputer (int id) throws DataAccessException {
		
		int result = 0;
		
		try {
			result = dao.deleteComputer(id);
			
			if (result == 0)
				throw new DataAccessException("Error lors de l'insert/update");
			
			JdbcConnection.getInstance().getConnection().commit();
		} catch (SQLException e) {
			logger.warn("Erreur lors de la suppression du computer " + e.getMessage());
			
			try {
				JdbcConnection.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
		}
		
		JdbcConnection.getInstance().closeConnection();
	}
	
	@Override
	public List<Company> getCompanies () {
		List<Company> companies = null;
		
		try {
			companies = daoCy.getCompanies();
		} catch (SQLException e) {
			logger.warn("Erreur lors de l'obtention de la liste des companies " + e.getMessage());
		}
		
		JdbcConnection.getInstance().closeConnection();
		return companies;
	}
	
	@Override
	public Company getCompany (int id) throws DataAccessException {
		Company c = null;
		
		try {
			c = daoCy.getCompany(id);
			
			if (c == null)
				throw new DataAccessException("Company inexistante");
			
		} catch (SQLException e) {
			logger.warn("Erreur lors de la récupération d'une company " + e.getMessage());
		}
		
		JdbcConnection.getInstance().closeConnection();
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
