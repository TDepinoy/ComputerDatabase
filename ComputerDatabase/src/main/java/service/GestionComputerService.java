package service;

import java.util.List;

import dao.GestionComputerDao;
import entites.Computer;

public class GestionComputerService {

	public static final int MAX_RESULTS_PER_PAGE = 10;
	
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
	
	public List<Computer> getComputers(int start) {
		return dao.getComputers(start, MAX_RESULTS_PER_PAGE);
	}
	
	public int countComputers() {
		return dao.countComputers();
	}
	
}
