package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.ComputerService;
import service.ComputerServiceImpl;

import com.mysql.jdbc.StringUtils;

import entites.Computer;
import exceptions.DataAccessException;

/**
 * Servlet implementation class validationServlet
 */
@SuppressWarnings("serial")
@WebServlet("/validationServlet")
public class ValidationServlet extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);
	
	private static final String standardClass = "clearfix ";
	private static final String errorClass = "clearfix error";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean error = false;
		ComputerService service = ComputerServiceImpl.getInstance();
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
		
		request.setAttribute("classCompany", standardClass);
		
		try {
			if (!StringUtils.isNullOrEmpty(request.getParameter("introduced"))) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				Date introduced = df.parse(request.getParameter("introduced"));
				c.setIntroduced(introduced);
				request.setAttribute("classIntroduced", standardClass);
			}
		} catch (ParseException e) {
			request.setAttribute("classIntroduced", errorClass);
			error = true;
		} 

		try {
			if (!StringUtils.isNullOrEmpty(request.getParameter("discontinued"))) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				Date discontinued = df.parse(request.getParameter("discontinued"));
				c.setDiscontinued(discontinued);
				request.setAttribute("classDiscontinued", standardClass);
			}
		} catch (ParseException e) {
			request.setAttribute("classDiscontinued", errorClass);
			error = true;
		}

		try {
			if (!companyId.isEmpty())
				c.setCompany(service.getCompany(Integer.parseInt(companyId)));
		
		
			request.setAttribute("computer", c);
	
			if (error) {
				request.setAttribute("companies", service.getCompanies());
				request.getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(request, response);
			} else {		
				service.insertOrUpdateComputer(c);
				response.sendRedirect("showComputers");
			}
		
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(request, response);
		}

	}

}
