package com.excilys.formation.computerDatabase.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.utils.OptionsRequest;


public interface GestionComputerDao {

	int deleteComputer(int id) throws DataAccessException;

	int insertComputer(Computer c) throws DataAccessException;

	int updateComputer(Computer c) throws DataAccessException;

	Computer getComputer(int id) throws DataAccessException;

	List<Computer> getComputers(int start, int maxResults, OptionsRequest or) throws DataAccessException; 

	int countComputers(String filter) throws DataAccessException;

}