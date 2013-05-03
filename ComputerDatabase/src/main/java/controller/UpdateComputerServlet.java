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

import service.GestionCompanyService;
import service.GestionComputerService;

/**
 * Servlet implementation class UpdateComputerServlet
 */
@WebServlet("/updateComputer")
public class UpdateComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("idComputer") == null)
			request.getServletContext().getRequestDispatcher("/showComputers").forward(request, response);
		else {
		
			GestionComputerService serviceComputer = GestionComputerService.getInstance();
			GestionCompanyService serviceCompany = GestionCompanyService.getInstance();
			
			
			int idComputer = Integer.parseInt(request.getParameter("idComputer"));
			Computer c = serviceComputer.getComputer(idComputer);
			request.setAttribute("computer", c);
			
			List<Company> companies = serviceCompany.getCompanies();		
			request.setAttribute("companies", companies);
			
			request.getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(request, response);
		}
	}
}
