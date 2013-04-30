package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entites.Computer;

import service.GestionComputerService;

/**
 * Servlet implementation class ShowComputerListServlet
 */
@WebServlet("/")
public class ShowComputerListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
 	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Computer> computers = GestionComputerService.getInstance().getComputers();
		request.setAttribute("computers", computers);
		request.setAttribute("totalComputers", computers.size());
		request.getServletContext().getRequestDispatcher("/WEB-INF/showComputers.jsp").forward(request, response);
	}


}
