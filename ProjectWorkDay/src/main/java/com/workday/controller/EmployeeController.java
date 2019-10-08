package com.workday.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workday.model.Employee;
import com.workday.model.Workday;
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
		List<Employee> employees = new ArrayList<Employee>(employeeService.findAll());

		model.addAttribute("employees", employees);
		return "list/list-employee";
	}
	
	@GetMapping("/list")
	public String listEmployee(Model model) {
		List<Employee> employees = new ArrayList<Employee>(employeeService.findAll());
		model.addAttribute("employees",employees);
		return "list/list-employee";
	}
	

	@GetMapping("/new")
	public String newemployee(Model model) {

		model.addAttribute("employee", new Employee());
		model.addAttribute("workdays", workdayService.findAll());
		return "create/form-employee";
	}

	@PostMapping("/new/submit")
	public String newEmployee(@Valid Employee employee, BindingResult bindingResult, Model model) {
		String url = "list/list-employee";
		if (bindingResult.hasErrors()) {
			model.addAttribute("workdays", workdayService.findAll());
			url = "create/form-employee";
		} else {
			employeeService.save(employee);
			List<Employee> employees = new ArrayList<Employee>(employeeService.findAll());
			model.addAttribute("employees",employees);

		}

		return url;
	}

	@GetMapping("/edit/{id}")
	public String editEmployee(@PathVariable("id") Long id, Model model) {

		String url = "";
		Employee employee = employeeService.findById(id);

		if (employee != null) {
			model.addAttribute("employee", employee);
			model.addAttribute("workdays", workdayService.findAll());
			url = "create/form-employee";

		} else {
			url = "redirect:/create/employee/";
		}
		return url;
	}
	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable ("id") Long id, Model model) {
		
		String url ="";
		Employee employee= employeeService.findById(id);
		
		
		if(employee != null) {
			employeeService.delete(id);
			url="redirect:/create/employee/";
		}
		return url;
	}

}
