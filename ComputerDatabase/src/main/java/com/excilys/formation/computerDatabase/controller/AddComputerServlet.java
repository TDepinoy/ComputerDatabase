package com.excilys.formation.computerDatabase.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.formation.computerDatabase.service.ComputerService;
import com.excilys.formation.computerDatabase.service.ComputerServiceImpl;


/**
 * Servlet implementation class AddComputerServlet
 */
@SuppressWarnings("serial")
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
		ComputerService service = context.getBean(ComputerService.class);
		context.close();
		
		request.setAttribute("companies", service.getCompanies());
		getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
		
	}
}
