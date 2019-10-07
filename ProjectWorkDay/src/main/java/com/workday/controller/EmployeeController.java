package com.workday.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workday.model.Employee;
import com.workday.services.EmployeeService;
import com.workday.services.WorkDayService;

@Controller
@RequestMapping("/create/employee")
public class EmployeeController {

	@Autowired
	private WorkDayService workdayService;
	@Autowired
	private EmployeeService employeeService;
	
	
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
	
	@PostMapping("/new/submit")
	public String newEmployee(@Valid Employee employee,BindingResult bindingResult, Model model ) {
		String url="index";
		if(bindingResult.hasErrors()) {
			model.addAttribute("workdays", workdayService.findAll());
			url= "create/form-employee";
		}else {
			employeeService.save(employee);
			
		}
		
		return url;
	}
}
