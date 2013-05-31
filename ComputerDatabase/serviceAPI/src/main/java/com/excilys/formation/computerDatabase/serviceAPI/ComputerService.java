package com.excilys.formation.computerDatabase.serviceAPI;

import java.util.List;

import com.excilys.formation.computerDatabase.entites.Company;
import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.entites.Page;
import com.excilys.formation.computerDatabase.utils.OptionsRequest;

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