package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entites.Computer;

import service.GestionComputerService;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@WebServlet("/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GestionComputerService service = GestionComputerService.getInstance();
		int idComputer = Integer.parseInt(request.getParameter("idComputer"));	
		service.deleteComputer(idComputer);
		
		request.getServletContext().getRequestDispatcher("/showComputers").forward(request, response);
	}
}