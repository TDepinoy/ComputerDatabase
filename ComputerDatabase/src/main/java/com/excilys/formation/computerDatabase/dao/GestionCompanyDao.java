package com.excilys.formation.computerDatabase.dao;


import java.util.List;

import org.springframework.dao.DataAccessException;

import com.excilys.formation.computerDatabase.entites.Company;


public interface GestionCompanyDao {

	List<Company> getCompanies() throws DataAccessException;

	Company getCompany(int id) throws DataAccessException;

}