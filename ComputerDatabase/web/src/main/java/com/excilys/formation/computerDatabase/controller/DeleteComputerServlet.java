package com.excilys.formation.computerDatabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.computerDatabase.serviceAPI.ComputerService;



/**
 * Servlet implementation class DeleteComputerServlet
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputerServlet {

	
	@Autowired
	private ComputerService service;
	
    @RequestMapping(method = RequestMethod.POST)
	public String doPost(@RequestParam("idComputer") int idComputer, Model model) {
		service.deleteComputer(idComputer);
		return "redirect:showComputers.html";
    }
}
