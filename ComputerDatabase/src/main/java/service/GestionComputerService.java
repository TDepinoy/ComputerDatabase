package service;

import java.util.List;

import utils.OptionsRequest;
import utils.Page;

import dao.GestionCompanyDao;
import dao.GestionComputerDao;
import entites.Company;
import entites.Computer;

public class GestionComputerService {
	
	private static GestionComputerService service;
	private GestionComputerDao dao;
	private GestionCompanyDao daoCy;
	
	static {
		service = new GestionComputerService();
	}
	
	private GestionComputerService () {
		dao = GestionComputerDao.getInstance();
		daoCy = GestionCompanyDao.getInstance();
	}
	
	public static GestionComputerService getInstance() {
		return service;
	}
	
	public Computer getComputer (int id) {
		return dao.getComputer(id);
	}
	
	public List<Computer> getComputers(int start, int maxResults, OptionsRequest or) {
		return dao.getComputers(start, maxResults, or);
	}
	
	public int countComputers(String filter) {
		return dao.countComputers(filter);
	}
	
	public void insertOrUpdateComputer (Computer c) {
		dao.insertOrUpdateComputer(c);
	}
	
	public void deleteComputer (int id) {
		dao.deleteComputer(id);
	}
	
	public List<Company> getCompanies () {
		return daoCy.getCompanies();
	}
	
	public Company getCompany (int id) {
		return daoCy.getCompany(id);
	}
	
	public Page createPage (int numPage, int maxResults, OptionsRequest or) {
		int totalResults = countComputers(or.getNameFilter());
		
		if (numPage < 0 || (numPage > 0 && (totalResults - numPage * maxResults < 0)))
			numPage = 0;
		
		return new Page (numPage, maxResults, totalResults, getComputers(numPage * maxResults, maxResults, or));
	}
	
}
