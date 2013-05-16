package dao;


import java.sql.SQLException;
import java.util.List;

import entites.Company;

public interface GestionCompanyDao {

	List<Company> getCompanies() throws SQLException;

	Company getCompany(int id) throws SQLException;

}