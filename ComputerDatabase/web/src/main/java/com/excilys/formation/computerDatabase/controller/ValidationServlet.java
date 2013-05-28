package com.excilys.formation.computerDatabase.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.excilys.formation.computerDatabase.serviceAPI.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.exceptions.DaoException;
import com.mysql.jdbc.StringUtils;


/**
 * Servlet implementation class validationServlet
 */
@Controller
@RequestMapping("validationServlet")
public class ValidationServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(ValidationServlet.class);
	
	private static final String standardClass = "clearfix ";
	private static final String errorClass = "clearfix error";
	
	@Autowired
	private ComputerService service;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(
			@RequestParam(value = "idComputer", required=false) Integer idComputer,
			@RequestParam(value = "name", defaultValue="") String name,
			@RequestParam(value = "company", defaultValue="") String idCompany,
			@RequestParam(value = "introduced") String introduced,
			@RequestParam(value = "discontinued") String discontinued,
			Model model) {
		
		boolean error = false;
		Computer c = new Computer();

		
		if (idComputer != null) {
			c.setId(idComputer);
		}


		if (StringUtils.isNullOrEmpty(name)) {
			model.addAttribute("className", errorClass);
			error = true;
		} else {
			model.addAttribute("className", standardClass);
			c.setName(name);
		}		
		
		model.addAttribute("classCompany", standardClass);
		
		try {
			if (!StringUtils.isNullOrEmpty(introduced)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				Date introducedDate = df.parse(introduced);
				c.setIntroduced(introducedDate);
				model.addAttribute("classIntroduced", standardClass);
			}
		} catch (ParseException e) {
			model.addAttribute("classIntroduced", errorClass);
			error = true;
		} 

		try {
			if (!StringUtils.isNullOrEmpty(discontinued)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				Date discontinuedDate = df.parse(discontinued);
				c.setDiscontinued(discontinuedDate);
				model.addAttribute("classDiscontinued", standardClass);
			}
		} catch (ParseException e) {
			model.addAttribute("classDiscontinued", errorClass);
			error = true;
		}

		try {
			if (!idCompany.isEmpty())
				c.setCompany(service.getCompany(Integer.parseInt(idCompany)));
		
		
			model.addAttribute("computer", c);
	
			if (error) {
				model.addAttribute("companies", service.getCompanies());
				return "updateComputer";
			} else {		
				service.insertOrUpdateComputer(c);
				return "redirect:showComputers.html";
			}
		
		} catch (DaoException e) {
			logger.warn(e.getMessage());
			model.addAttribute("error", e.getMessage());
			return"errorPage";
		}

	}

}
