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

import service.ComputerServiceImpl;

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
		
		int idComputer = 0;
		
		try {
			idComputer = Integer.parseInt(request.getParameter("idComputer"));
			
			Computer c = ComputerServiceImpl.getInstance().getComputer(idComputer);
			List<Company> companies = ComputerServiceImpl.getInstance().getCompanies();
			
			request.setAttribute("computer", c);		
			request.setAttribute("companies", companies);
			
			request.getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(request, response);
			
		} catch (NumberFormatException e) {
			response.sendRedirect("showComputers");
		}
	}
}
