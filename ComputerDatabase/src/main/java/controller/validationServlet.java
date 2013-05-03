package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.GestionCompanyService;
import service.GestionComputerService;

import com.mysql.jdbc.StringUtils;

import entites.Company;
import entites.Computer;

/**
 * Servlet implementation class validationServlet
 */
@WebServlet("/validationServlet")
public class validationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String standardClass = "clearfix ";
	private static final String errorClass = "clearfix error";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean error = false;
		GestionComputerService service = GestionComputerService.getInstance();
		GestionCompanyService serviceComp = GestionCompanyService.getInstance();
		
		if (request.getParameter("idComputer") == null) {
			System.out.println("bonjour");
			request.getServletContext().getRequestDispatcher("/").forward(request, response);
			
		}
		else {
			
			Computer c = new Computer ();
			c.setId(Integer.parseInt(request.getParameter("idComputer")));
			String name = request.getParameter("name");
			String companyId = request.getParameter("company");
			
			if (StringUtils.isNullOrEmpty(name)) {
				request.setAttribute("className", errorClass);
				error = true;
			}
			else {
				request.setAttribute("className", standardClass);
				c.setName(name);
			}
			
			if (StringUtils.isNullOrEmpty(companyId)) {
				request.setAttribute("classCompany", errorClass);
				error = true;
			}
			else {
				request.setAttribute("classCompany", standardClass);
				Company cy = serviceComp.getCompany(Integer.parseInt(companyId));
				c.setCompany(cy);
			}

			try {
				Date introduced = new SimpleDateFormat ("yyyy-MM-dd").parse(request.getParameter("introduced"));
				c.setIntroduced(introduced);
			} catch (ParseException e) {
				Logger.getLogger("main").log(Level.SEVERE, "Erreur de parsing de date");
				e.printStackTrace();
				request.setAttribute("classIntroduced", errorClass);
				error = true;
			} catch (RuntimeException e) {
				e.printStackTrace();
				c.setIntroduced(null);
			}

			try {
				Date discontinued = new SimpleDateFormat ("yyyy-MM-dd").parse(request.getParameter("discontinued"));
				c.setDiscontinued(discontinued);
			} catch (ParseException e) {
				Logger.getLogger("main").log(Level.SEVERE, "Erreur de parsing de date");
				e.printStackTrace();
				request.setAttribute("classDiscontinued", errorClass);
				error = true;
			} catch (RuntimeException e) {
				e.printStackTrace();
				c.setDiscontinued(null);
			}
			
			request.setAttribute("computer", c);
			
			if (error) {
				request.setAttribute("companies", serviceComp.getCompanies());
				request.getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(request, response);
			}
			else {		
				service.updateComputer(c);
				request.setAttribute("updateDone", "done");
				request.getServletContext().getRequestDispatcher("/showComputers").forward(request, response);
			}
		}
	}

}
