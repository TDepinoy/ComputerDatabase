package com.excilys.formation.computerDatabase.dao;


import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.computerDatabase.entites.Company;


public interface GestionCompanyDao {

	List<Company> getCompanies() throws SQLException;

	Company getCompany(int id) throws SQLException;

}