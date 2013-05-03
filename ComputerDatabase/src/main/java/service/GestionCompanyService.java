package service;

import java.util.List;

import dao.GestionCompanyDao;
import dao.GestionComputerDao;
import entites.Company;


public class GestionCompanyService {

	private static GestionCompanyService service;
	private GestionCompanyDao dao;
	
	static {
		service = new GestionCompanyService();
	}
	
	private GestionCompanyService () {
		dao = GestionCompanyDao.getInstance();
	}
	
	public static GestionCompanyService getInstance () {
		return service;
	}
	
	public List<Company> getCompanies () {
		return dao.getCompanies();
	}
	
	public Company getCompany (int id) {
		return dao.getCompany(id);
	}
	
}
