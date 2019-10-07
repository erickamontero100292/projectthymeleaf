package com.workday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workday.model.Employee;
import com.workday.services.WorkDayService;

@Controller
@RequestMapping("/create/employee")
public class EmployeeController {

	@Autowired
	private WorkDayService workdayService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("employee", new Employee());
		return "create/form-employee";
	}
	
	@GetMapping("/new")
	public String newemployee(Model model) {
		
		model.addAttribute("employee", new Employee());
		model.addAttribute("workdays",workdayService.findAll());
		return "create/form-employee";
	}
	
}
