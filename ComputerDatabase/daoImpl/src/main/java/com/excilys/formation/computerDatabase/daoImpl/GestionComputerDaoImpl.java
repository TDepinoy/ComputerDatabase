package com.excilys.formation.computerDatabase.daoImpl;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;


import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerDatabase.daoAPI.GestionComputerDao;
import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.utils.OptionsRequest;
import com.mysql.jdbc.StringUtils;

@Repository
public class GestionComputerDaoImpl implements GestionComputerDao {
	
	private static final String SELECT_ALL_COMPUTERS = "from Computer c";
	private static final String SELECT_ONE_COMPUTER = "from Computer c where c.id= :id";
	private static final String COUNT_COMPUTERS = "select count(c.id) from Computer c";
	private static final String WHERE_FILTER_NAME_STR = " where c.name like :filter";
	/**
	 * 1$: colonne sur laquelle s'effectue le tri, 2$: Ordre de tri (asc/desc)
	 */
	private static final String ORDER_BY_LIMIT_STR = " ORDER BY ISNULL (%1$s), %1$s %2$s";
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void deleteComputer(int id) {
		sessionFactory.getCurrentSession().delete(getComputer(id));
	}
	
	@Override
	public void insertComputer(Computer c) {
		sessionFactory.getCurrentSession().save(c);
	}
	
	@Override
	public void updateComputer (Computer c) {
		sessionFactory.getCurrentSession().update(c);
	}

	@Override
	public Computer getComputer(int id) {
    	return (Computer) sessionFactory.getCurrentSession().createQuery(SELECT_ONE_COMPUTER).setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Computer> getComputers(int start, int maxResults,
			OptionsRequest or) {
		Formatter f = new Formatter();
		StringBuilder query = new StringBuilder(SELECT_ALL_COMPUTERS);
		List<Computer> computers = new ArrayList<Computer>();

		if (!StringUtils.isNullOrEmpty(or.getNameFilter()))
			query.append(WHERE_FILTER_NAME_STR);

		f.format(ORDER_BY_LIMIT_STR, or.getOrderC(), or.getOrderW());
		query.append(f.toString());

		if (!StringUtils.isNullOrEmpty(or.getNameFilter()))
			computers = sessionFactory.getCurrentSession()
					.createQuery(query.toString()).setFirstResult(start)
					.setMaxResults(maxResults).setString("filter", or.getNameFilter())
					.list();
		else
			computers = sessionFactory.getCurrentSession()
					.createQuery(query.toString())
					.setFirstResult(start)
					.setMaxResults(maxResults)
					.list();

		f.close();

		return computers;
	}

	@Override
	public int countComputers (String filter) {
			
		StringBuilder query = new StringBuilder (COUNT_COMPUTERS);
		
		if (filter != null && !filter.isEmpty()) 
			query.append(WHERE_FILTER_NAME_STR);

		Query q = sessionFactory.getCurrentSession().createQuery(query.toString());
		
		if (filter != null && !filter.isEmpty()) {
			q.setString("filter", filter);
		}

		return ((Long)q.uniqueResult()).intValue();
	}
}
