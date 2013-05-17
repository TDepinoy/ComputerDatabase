package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ComputerServiceImpl;

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
		
		request.setAttribute("companies", ComputerServiceImpl.getInstance().getCompanies());
		request.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
		
	}
}
