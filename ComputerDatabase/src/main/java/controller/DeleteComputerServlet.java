package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.DataAccessException;

import service.ComputerServiceImpl;
import service.ComputerService;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@SuppressWarnings("serial")
@WebServlet("/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService service = ComputerServiceImpl.getInstance();
		int idComputer = Integer.parseInt(request.getParameter("idComputer"));	
		
		try {
			service.deleteComputer(idComputer);
		} catch (DataAccessException e) {
			logger.warn("Erreur lors de la suppression d'un computer " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(request, response);
		}
		
		response.sendRedirect("showComputers");
	}
}
