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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
	
	private ApplicationContext context;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		if (context == null){
	        context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        }
		
		ComputerService service = context.getBean(ComputerService.class);

		int idComputer = 0;
		
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
