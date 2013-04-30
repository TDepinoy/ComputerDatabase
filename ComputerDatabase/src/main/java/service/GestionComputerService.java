package service;

import java.util.List;

import dao.GestionComputerDao;
import entites.Computer;

public class GestionComputerService {

	private static GestionComputerService service;
	private GestionComputerDao dao;
	
	static {
		service = new GestionComputerService();
	}
	
	private GestionComputerService () {
		dao = GestionComputerDao.getInstance();
	}
	
	public static GestionComputerService getInstance() {
		return service;
	}
	
	public List<Computer> getComputers() {
		return dao.getComputers();
	}
	
}
