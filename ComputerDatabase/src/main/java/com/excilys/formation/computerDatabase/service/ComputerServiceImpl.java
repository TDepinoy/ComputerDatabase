package com.excilys.formation.computerDatabase.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerDatabase.dao.GestionCompanyDao;
import com.excilys.formation.computerDatabase.dao.GestionComputerDao;
import com.excilys.formation.computerDatabase.entites.Company;
import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.entites.Page;
import com.excilys.formation.computerDatabase.exceptions.DaoException;
import com.excilys.formation.computerDatabase.utils.OptionsRequest;


@Service
@Transactional(readOnly=true)
public class ComputerServiceImpl implements ComputerService {
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	
	@Autowired
	private GestionComputerDao dao;
	
	@Autowired
	private GestionCompanyDao daoCy;
	
	public ComputerServiceImpl () {}

	
	@Override
	public Computer getComputer (int id) throws DaoException {	
		Computer c = null;
		
		try {
			c = dao.getComputer(id);
			
			if (c == null)
				throw new DaoException("Computer inexistant");
			
		} catch (DataAccessException e) {
			logger.warn("Erreur lors de la récupération du computer " + e.getMessage());
		}
		
		return c;
	}
	
	@Override
	public List<Computer> getComputers(int start, int maxResults, OptionsRequest or) {
		List<Computer> computers = null;
		
		try {
			computers = dao.getComputers(start, maxResults, or);
		} catch (DataAccessException e) {
			logger.warn("Erreur lors de l'obtention de la liste des computers " + e.getMessage());
		}
		
		return computers;
	}
	
	@Override
	public int countComputers(String filter) {
		int totalResults = 0;
		
		try {
			totalResults = dao.countComputers(filter);
		} catch (DataAccessException e) {
			logger.warn("Erreur lors du count computers" + e.getMessage());
		}
		
		return totalResults;
	}

	@Override
	@Transactional(readOnly=false)
	public void insertOrUpdateComputer (Computer c) throws DaoException {
		
		int result = 0;
		
		try {
			if (c.getId() > 0)
				result = dao.updateComputer(c);
			else 
				result = dao.insertComputer(c);
			
			if (result == 0)
				throw new DaoException("Error lors de l'insert/update");
			
			
		} catch (DataAccessException e) {
			logger.warn("Erreur lors de l'insertion ou la mise à jour d'un computer " + e.getMessage());

		}
	}
	
	@Override
	@Transactional(readOnly=false)
	public void deleteComputer (int id) throws DaoException {
		
		int result = 0;
		
		try {
			result = dao.deleteComputer(id);
			
			if (result == 0)
				throw new DaoException("Error lors de l'insert/update");
			
		} catch (DataAccessException e) {
			logger.warn("Erreur lors de la suppression du computer " + e.getMessage());
		}
	}
	
	@Override
	public List<Company> getCompanies () {
		List<Company> companies = null;
		
		try {
			companies = daoCy.getCompanies();
		} catch (DataAccessException e) {
			logger.warn("Erreur lors de l'obtention de la liste des companies " + e.getMessage());
		}
		
		return companies;
	}
	
	@Override
	public Company getCompany (int id) throws DaoException {
		Company c = null;
		
		try {
			c = daoCy.getCompany(id);
			
			if (c == null)
				throw new DaoException("Company inexistante");
			
		} catch (DataAccessException e) {
			logger.warn("Erreur lors de la récupération d'une company " + e.getMessage());
		}

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
