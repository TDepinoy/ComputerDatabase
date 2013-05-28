package com.excilys.formation.computerDatabase.controller;

import com.excilys.formation.computerDatabase.serviceAPI.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.computerDatabase.entites.Page;
import com.excilys.formation.computerDatabase.utils.OptionsRequest;

@Controller
@RequestMapping("showComputers")
public class ShowComputerListServlet {
	public static final int MAX_RESULTS_PER_PAGE = 10;

	@Autowired
	private ComputerService service;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(
			@RequestParam(value = "p", defaultValue = "0") Integer pageNumber,
			@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "s", defaultValue = "1") Integer sort,
			Model model) {

		
		Page page = service.createPage(pageNumber, MAX_RESULTS_PER_PAGE, new OptionsRequest(filter, sort));

		model.addAttribute("page", page);
		model.addAttribute("s", sort);
		model.addAttribute("filter", filter);

		return "showComputers";
	}
}
