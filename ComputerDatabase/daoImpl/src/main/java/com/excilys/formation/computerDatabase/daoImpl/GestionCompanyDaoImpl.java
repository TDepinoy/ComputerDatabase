package com.excilys.formation.computerDatabase.daoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerDatabase.daoAPI.GestionCompanyDao;
import com.excilys.formation.computerDatabase.entites.Company;


@Repository
public class GestionCompanyDaoImpl implements GestionCompanyDao {

    private static final String SELECT_ALL_COMPANIES = "from Company ORDER BY name";
    private static final String SELECT_ONE_COMPANY = "from Company cy where cy.id= :id";

    @Autowired
    private SessionFactory sessionFactory;


	@SuppressWarnings("unchecked")
	@Override
    public List<Company> getCompanies() {
    	return sessionFactory.getCurrentSession().createQuery(SELECT_ALL_COMPANIES).list();
    }

    @Override
    public Company getCompany(int id) {
    	return (Company) sessionFactory.getCurrentSession().createQuery(SELECT_ONE_COMPANY).setInteger("id", id).uniqueResult();
    }
}
