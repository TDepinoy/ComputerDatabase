package service;

import java.util.List;

import utils.OptionsRequest;
import entites.Company;
import entites.Computer;
import entites.Page;

public interface ComputerService {

	Computer getComputer(int id);

	List<Computer> getComputers(int start, int maxResults, OptionsRequest or);

	int countComputers(String filter);

	void insertOrUpdateComputer(Computer c);

	void deleteComputer(int id);

	List<Company> getCompanies();

	Company getCompany(int id);

	Page createPage(int numPage, int maxResults, OptionsRequest or);

}