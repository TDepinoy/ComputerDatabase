package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.StringUtils;

import service.GestionComputerService;
import utils.OptionsRequest;
import entites.Computer;

/**
 * Servlet implementation class ShowComputerListServlet
 */
@WebServlet("/showComputers")
public class ShowComputerListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
 	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Computer> computers;
		
		int sort;
		String currentIndex = request.getParameter("currentIndex");
		String nameFilter = request.getParameter("filter");
		
		int total = GestionComputerService.getInstance().countComputers(nameFilter);
		
		try {
			sort = Integer.parseInt(request.getParameter("s"));
		} catch (NumberFormatException e) {
			sort = 1;
		}
		
		int displayFrom = 0;
		int displayTo = 0;
		
		if (currentIndex == null) {
			displayFrom = 1;
			displayTo = GestionComputerService.MAX_RESULTS_PER_PAGE;
			computers = GestionComputerService.getInstance().getComputers(0, new OptionsRequest(nameFilter, sort));
			request.setAttribute("currentIndex", 0);
		}
		else {
			
			int index = 0;
			
			try {
				index = Integer.parseInt(currentIndex);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			
			displayFrom = index * GestionComputerService.MAX_RESULTS_PER_PAGE + 1;
			displayTo = (index + 1 )* GestionComputerService.MAX_RESULTS_PER_PAGE;
			
			
			if (index < 0) {
				index = 0;
				displayFrom = 1;
				displayTo =  GestionComputerService.MAX_RESULTS_PER_PAGE;
			}
			else if ( (index + 1 ) * GestionComputerService.MAX_RESULTS_PER_PAGE > total) {
				index = total / GestionComputerService.MAX_RESULTS_PER_PAGE;
				displayFrom = total - GestionComputerService.MAX_RESULTS_PER_PAGE;
				displayTo =  total;	
			}			
			
			computers = GestionComputerService.getInstance().getComputers(index, new OptionsRequest(nameFilter, sort));
			currentIndex = Integer.toString(index);
			
		}
		
		request.setAttribute("s", sort);
		request.setAttribute("filter", nameFilter);
		request.setAttribute("currentIndex", currentIndex);
		request.setAttribute("from", displayFrom);
		request.setAttribute("to", displayTo);
		request.setAttribute("computers", computers);
		request.setAttribute("totalComputers", total);
		request.getServletContext().getRequestDispatcher("/WEB-INF/showComputers.jsp").forward(request, response);
	}
}
