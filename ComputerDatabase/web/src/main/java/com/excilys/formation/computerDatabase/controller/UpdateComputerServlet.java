package com.excilys.formation.computerDatabase.controller;

import java.util.List;

import com.excilys.formation.computerDatabase.serviceAPI.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.computerDatabase.entites.Company;
import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.exceptions.DaoException;



/**
 * Servlet implementation class UpdateComputerServlet
 */
@Controller
@RequestMapping("updateComputer")
public class UpdateComputerServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateComputerServlet.class);
	
	@Autowired
	private ComputerService service;
	
	@RequestMapping
	public String doGet(@RequestParam(value = "idComputer", defaultValue = "0") Integer idComputer, Model model) {
	
		try {
			
			Computer c = service.getComputer(idComputer);
			List<Company> companies = service.getCompanies();
			
			model.addAttribute("computer", c);		
			model.addAttribute("companies", companies);
			
			return "updateComputer";
			
		} catch (DaoException e) {
			logger.warn(e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "errorPage";
		}
	}
}
