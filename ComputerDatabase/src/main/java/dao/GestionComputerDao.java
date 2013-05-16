package dao;

import java.sql.SQLException;
import java.util.List;

import utils.OptionsRequest;
import entites.Computer;

public interface GestionComputerDao {

	void deleteComputer(int id) throws SQLException;

	void insertOrUpdateComputer(Computer c) throws SQLException;

	void insertComputer(Computer c) throws SQLException;

	void updateComputer(Computer c) throws SQLException;

	Computer getComputer(int id) throws SQLException;

	List<Computer> getComputers(int start, int maxResults, OptionsRequest or) throws SQLException; 

	int countComputers(String filter) throws SQLException;

}