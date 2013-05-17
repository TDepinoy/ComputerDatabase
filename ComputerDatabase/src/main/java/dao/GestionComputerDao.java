package dao;

import java.sql.SQLException;
import java.util.List;

import utils.OptionsRequest;
import entites.Computer;

public interface GestionComputerDao {

	int deleteComputer(int id) throws SQLException;

	int insertComputer(Computer c) throws SQLException;

	int updateComputer(Computer c) throws SQLException;

	Computer getComputer(int id) throws SQLException;

	List<Computer> getComputers(int start, int maxResults, OptionsRequest or) throws SQLException; 

	int countComputers(String filter) throws SQLException;

}