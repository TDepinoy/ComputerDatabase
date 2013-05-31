package com.excilys.formation.computerDatabase.controller;

import com.excilys.formation.computerDatabase.entites.Company;
import com.excilys.formation.computerDatabase.entites.Computer;
import com.excilys.formation.computerDatabase.serviceAPI.ComputerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utils.StringToCompanyConverter;


/**
 * Servlet implementation class AddComputerServlet
 */
@Controller
@RequestMapping("/addComputer")
public class AddComputerServlet {
       
	private static final Logger logger = LoggerFactory.getLogger(UpdateComputerServlet.class);
	
	@Autowired
	private ComputerService service;

 	
    @RequestMapping(method = RequestMethod.GET)
	public String doGet(Model model) {
		model.addAttribute("companies", service.getCompanies());
		model.addAttribute("computer", new Computer());
		return "addComputer";		
	}
    
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@ModelAttribute("computer") Computer computer,
			BindingResult res, Model model,
			RedirectAttributes redirectAttributes) {
		
		if (res.hasErrors()) {
			logger.warn(res.getAllErrors().toString());
			model.addAttribute("res", res);
			model.addAttribute("companies", service.getCompanies());
			return "addComputer";
		}
		else {
			service.insertOrUpdateComputer(computer);
			return "redirect:showComputers.html";
		}
	}
    
	@InitBinder
	public void initBinderUser(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new StringToCompanyConverter(service));
	}
}
