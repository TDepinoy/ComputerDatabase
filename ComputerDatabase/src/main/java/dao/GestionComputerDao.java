package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import utils.OptionsRequest;
import entites.Computer;

public interface GestionComputerDao {

	void deleteComputer(Connection conn, int id) throws SQLException;

	void insertOrUpdateComputer(Connection conn, Computer c) throws SQLException;

	void insertComputer(Connection conn, Computer c) throws SQLException;

	void updateComputer(Connection conn, Computer c) throws SQLException;

	Computer getComputer(Connection conn, int id) throws SQLException;

	List<Computer> getComputers(Connection conn, int start, int maxResults, OptionsRequest or) throws SQLException; 

	int countComputers(Connection conn, String filter) throws SQLException;

}