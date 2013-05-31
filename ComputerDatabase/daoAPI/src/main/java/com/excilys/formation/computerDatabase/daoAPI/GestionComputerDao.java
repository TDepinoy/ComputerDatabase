package com.excilys.formation.computerDatabase.daoAPI;

import java.util.List;

import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.utils.OptionsRequest;


public interface GestionComputerDao {

	boolean deleteComputer(int id);

	boolean insertComputer(Computer c);

	boolean updateComputer(Computer c);

	Computer getComputer(int id);

	List<Computer> getComputers(int start, int maxResults, OptionsRequest or); 

	int countComputers(String filter);

}