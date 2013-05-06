package service;

import java.util.List;

import utils.OptionsRequest;

import dao.GestionCompanyDao;
import dao.GestionComputerDao;
import entites.Company;
import entites.Computer;

public class GestionComputerService {

	public static final int MAX_RESULTS_PER_PAGE = 10;
	
	private static GestionComputerService service;
	private GestionComputerDao dao;
	private GestionCompanyDao daoCy;
	
	static {
		service = new GestionComputerService();
	}
	
	private GestionComputerService () {
		dao = GestionComputerDao.getInstance();
	}
	
	public static GestionComputerService getInstance() {
		return service;
	}
	
	public Computer getComputer (int id) {
		return dao.getComputer(id);
	}
	
	public List<Computer> getComputers(int start, OptionsRequest or) {
		return dao.getComputers(start, MAX_RESULTS_PER_PAGE, or);
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
	
}
