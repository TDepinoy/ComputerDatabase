package com.excilys.formation.computerDatabase.daoAPI;


import com.excilys.formation.computerDatabase.entites.Company;
import org.springframework.dao.DataAccessException;

import java.util.List;



public interface GestionCompanyDao {

	List<Company> getCompanies() throws DataAccessException;

	Company getCompany(int id) throws DataAccessException;

}