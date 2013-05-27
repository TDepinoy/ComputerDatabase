package com.excilys.formation.computerDatabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.formation.computerDatabase.service.ComputerService;


/**
 * Servlet implementation class AddComputerServlet
 */
@Controller
@RequestMapping("/ComputerDatabase/delete")
public class AddComputerServlet {
       
	@Autowired
	private ComputerService service;
	

	public String doGet(Model model) {
		model.addAttribute("companies", service.getCompanies());
		return "addComputer";		
	}
}
