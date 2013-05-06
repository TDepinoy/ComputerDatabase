package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entites.Company;
import entites.Computer;

import service.GestionComputerService;

/**
 * Servlet implementation class UpdateComputerServlet
 */
@WebServlet("/updateComputer")
public class UpdateComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ADD_COMPUTER_URL = "/WEB-INF/addComputer.jsp";
	private static final String UPDATE_COMPUTER_URL = "/WEB-INF/updateComputer.jsp";
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		GestionComputerService service = GestionComputerService.getInstance();
	
		String url;
		
		if (request.getParameter("idComputer") == null) {
			url = ADD_COMPUTER_URL;
		}
		else {
			Computer c = service.getComputer(Integer.parseInt(request.getParameter("idComputer")));
			request.setAttribute("computer", c);
			url = UPDATE_COMPUTER_URL;
		}
		
		List<Company> companies = service.getCompanies();		
		request.setAttribute("companies", companies);
				
		request.getServletContext().getRequestDispatcher(url).forward(request, response);
	}
}
