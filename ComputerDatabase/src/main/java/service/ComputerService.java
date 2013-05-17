package service;

import java.util.List;

import utils.OptionsRequest;
import entites.Company;
import entites.Computer;
import entites.Page;
import exceptions.DataAccessException;

public interface ComputerService {

	Computer getComputer(int id)  throws DataAccessException;

	List<Computer> getComputers(int start, int maxResults, OptionsRequest or);

	int countComputers(String filter);

	void insertOrUpdateComputer(Computer c) throws DataAccessException;

	void deleteComputer(int id) throws DataAccessException;

	List<Company> getCompanies();

	Company getCompany(int id)  throws DataAccessException;

	Page createPage(int numPage, int maxResults, OptionsRequest or);

}