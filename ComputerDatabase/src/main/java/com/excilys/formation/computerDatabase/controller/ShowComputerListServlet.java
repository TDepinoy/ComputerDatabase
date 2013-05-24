package com.excilys.formation.computerDatabase.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.formation.computerDatabase.entites.Page;
import com.excilys.formation.computerDatabase.service.ComputerService;
import com.excilys.formation.computerDatabase.utils.OptionsRequest;



/**
 * Servlet implementation class ShowComputerListServlet
 */
@SuppressWarnings("serial")
@WebServlet("/showComputers")
public class ShowComputerListServlet extends HttpServlet {
	public static final int MAX_RESULTS_PER_PAGE = 10;   
	
	private ApplicationContext context;
	
 	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (context == null){
	        context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        }
		
		ComputerService service = context.getBean(ComputerService.class);
		
		int sort;
		int pageNumber = 0;
		String page = request.getParameter("p");
		String nameFilter = request.getParameter("filter");
		
		try {
			sort = Integer.parseInt(request.getParameter("s"));
		} catch (NumberFormatException e) {
			sort = 1;
		}
			
		try {
			pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			pageNumber = 0;
		}
		
		Page p = service.createPage (pageNumber, MAX_RESULTS_PER_PAGE, new OptionsRequest(nameFilter, sort));

		request.setAttribute("page", p);
		request.setAttribute("s", sort);
		request.setAttribute("filter", nameFilter);
		
		getServletContext().getRequestDispatcher("/WEB-INF/showComputers.jsp").forward(request, response);
	}
}
