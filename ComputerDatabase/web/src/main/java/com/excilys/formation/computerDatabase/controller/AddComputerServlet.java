package com.excilys.formation.computerDatabase.controller;

import com.excilys.formation.computerDatabase.serviceAPI.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Servlet implementation class AddComputerServlet
 */
@Controller
@RequestMapping("/addComputer")
public class AddComputerServlet {
       
	@Autowired
	private ComputerService service;

    @RequestMapping(method = RequestMethod.GET)
	public String doGet(Model model) {
		model.addAttribute("companies", service.getCompanies());
		return "addComputer";		
	}
}
