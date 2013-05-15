package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ComputerServiceImpl;
import service.ComputerService;

import com.mysql.jdbc.StringUtils;

import entites.Company;
import entites.Computer;

/**
 * Servlet implementation class validationServlet
 */
@WebServlet("/validationServlet")
public class ValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String standardClass = "clearfix ";
	private static final String errorClass = "clearfix error";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean error = false;
		ComputerService service = ComputerServiceImpl.getInstance();
		Company cy;
		Computer c = new Computer();

		String idComputer = request.getParameter("idComputer");
		
		if (!StringUtils.isNullOrEmpty(idComputer)) {
			c.setId(Integer.parseInt(request.getParameter("idComputer")));
		}

		String name = request.getParameter("name");
		String companyId = request.getParameter("company");

		if (StringUtils.isNullOrEmpty(name)) {
			request.setAttribute("className", errorClass);
			error = true;
		} else {
			request.setAttribute("className", standardClass);
			c.setName(name);
		}

		if (StringUtils.isNullOrEmpty(companyId)) 
			 cy = null;
		else 	
			cy = service.getCompany(Integer.parseInt(companyId));		
		
		request.setAttribute("classCompany", standardClass);
		c.setCompany(cy);
		
		try {
			if (!StringUtils.isNullOrEmpty(request.getParameter("introduced"))) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				Date introduced = df.parse(request.getParameter("introduced"));
				c.setIntroduced(introduced);
				request.setAttribute("classIntroduced", standardClass);
			}
		} catch (ParseException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Erreur de parsing de date");
			e.printStackTrace();
			request.setAttribute("classIntroduced", errorClass);
			error = true;
		} 

		try {
			if (!StringUtils.isNullOrEmpty(request.getParameter("discontinued"))) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				Date introduced = df.parse(request.getParameter("discontinued"));
				c.setIntroduced(introduced);
				request.setAttribute("classDiscontinued", standardClass);
			}
		} catch (ParseException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Erreur de parsing de date");
			e.printStackTrace();
			request.setAttribute("classDiscontinued", errorClass);
			error = true;
		}

		request.setAttribute("computer", c);

		if (error) {
			request.setAttribute("companies", service.getCompanies());
			request.getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(request, response);
		} else {
			service.insertOrUpdateComputer(c);
			response.sendRedirect("showComputers");
		}

	}

}
