package com.excilys.formation.computerDatabase.controller;

import com.excilys.formation.computerDatabase.serviceAPI.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.computerDatabase.exceptions.DaoException;



/**
 * Servlet implementation class DeleteComputerServlet
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputerServlet {

	private static final Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);
	
	@Autowired
	private ComputerService service;

    @RequestMapping(method = RequestMethod.POST)
	public String doPost(@RequestParam("idComputer") int idComputer, Model model) {
				
		try {
			service.deleteComputer(idComputer);
			return "redirect:showComputers.html";
		} catch (DaoException e) {
			logger.warn("Erreur lors de la suppression d'un computer " + e.getMessage());
			return "error";
		}
	}
}
