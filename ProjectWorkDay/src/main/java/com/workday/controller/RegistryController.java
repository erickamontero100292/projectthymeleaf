package com.workday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.workday.model.Registry;
import com.workday.services.EmployeeService;

@Controller
@RequestMapping("/create/registry")
public class RegistryController {
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/new")
	public String newemployee(Model model) {

		model.addAttribute("registry", new Registry());
		model.addAttribute("employees", employeeService.findAll());
		return "create/form-registry";
	}
}
