package com.excilys.formation.computerDatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.formation.computerDatabase.entites.Company;
import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.exceptions.DaoException;
import com.excilys.formation.computerDatabase.service.ComputerService;



/**
 * Servlet implementation class UpdateComputerServlet
 */
@SuppressWarnings("serial")
@WebServlet("/updateComputer")
public class UpdateComputerServlet extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateComputerServlet.class);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idComputer = 0;
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
		ComputerService service = context.getBean(ComputerService.class);
		context.close();
		
		try {
			idComputer = Integer.parseInt(request.getParameter("idComputer"));
			
			Computer c = service.getComputer(idComputer);
			List<Company> companies = service.getCompanies();
			
			request.setAttribute("computer", c);		
			request.setAttribute("companies", companies);
			
			getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(request, response);
			
		} catch (NumberFormatException e) {
			response.sendRedirect("showComputers");
		} catch (DaoException e) {
			logger.warn(e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(request, response);
		}
	}
}
