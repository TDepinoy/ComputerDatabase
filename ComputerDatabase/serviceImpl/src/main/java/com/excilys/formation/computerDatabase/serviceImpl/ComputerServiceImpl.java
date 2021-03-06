package com.excilys.formation.computerDatabase.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.excilys.formation.computerDatabase.daoAPI.GestionCompanyDao;
import com.excilys.formation.computerDatabase.daoAPI.GestionComputerDao;
import com.excilys.formation.computerDatabase.entites.Company;
import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.entites.Page;
import com.excilys.formation.computerDatabase.serviceAPI.ComputerService;
import com.excilys.formation.computerDatabase.utils.OptionsRequest;


@Service
@Transactional(readOnly=true)
public class ComputerServiceImpl implements ComputerService {
	
    @Autowired
	private GestionComputerDao dao;

    @Autowired
	private GestionCompanyDao daoCy;
	
	
	@Override
	public Computer getComputer (int id) {	
		Computer c = dao.getComputer(id);
		Assert.notNull(c, "Assert Erreur : Computer inexistant");
		return c;
	}
	
	@Override
	public List<Computer> getComputers(int start, int maxResults, OptionsRequest or) {
		return dao.getComputers(start, maxResults, or);
	}
	
	@Override
	public int countComputers(String filter) {
		return dao.countComputers(filter);
	}

	@Override
	@Transactional(readOnly=false)
	public void insertOrUpdateComputer (Computer c) {
		if (c.getId() > 0)
			dao.updateComputer(c);
		else 
			dao.insertComputer(c);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void deleteComputer (int id) {
		dao.deleteComputer(id);
	}
	
	@Override
	public List<Company> getCompanies () {
		List<Company> companies = daoCy.getCompanies();
		Assert.notNull(companies, "Assert Erreur lors de la récupération de la liste des companies");
		return companies;
	}
	
	@Override
	public Company getCompany (int id) {
		Company c = daoCy.getCompany(id);
		Assert.notNull(c, "Assert Erreur : Company inexistante");
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
