package com.excilys.formation.computerDatabase.controller;

import java.io.IOException;
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

import com.excilys.formation.computerDatabase.exceptions.DaoException;
import com.excilys.formation.computerDatabase.service.ComputerService;



/**
 * Servlet implementation class DeleteComputerServlet
 */
@SuppressWarnings("serial")
@WebServlet("/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);
	
	private ApplicationContext context;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (context == null){
	        context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        }
		
		ComputerService service = context.getBean(ComputerService.class);

		
		int idComputer = Integer.parseInt(request.getParameter("idComputer"));	
		
		try {
			service.deleteComputer(idComputer);
			response.sendRedirect("showComputers");
		} catch (DaoException e) {
			logger.warn("Erreur lors de la suppression d'un computer " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(request, response);
		}
	}
}
