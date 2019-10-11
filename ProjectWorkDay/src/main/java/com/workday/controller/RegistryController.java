package com.workday.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workday.model.Registry;
import com.workday.model.Workday;
import com.workday.services.EmployeeService;
import com.workday.services.RegistryService;

@Controller
@RequestMapping("/create/registry")
public class RegistryController {
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RegistryService registryService;
	
	

	@GetMapping("/")
	public String index(Model model) {
		List<Registry> registrys = new ArrayList<Registry>(registryService.findAll());

		model.addAttribute("registrys", registrys);
		return "list/list-registry";
	}
	

	@GetMapping("/list")
	public String listRegistry(Model model) {
		List<Registry> registrys = new ArrayList<Registry>(registryService.findAll());

		model.addAttribute("registrys", registrys);
		return "list/list-registry";
	}
	
	
	@GetMapping("/new")
	public String newemployee(Model model) {

		model.addAttribute("registry", new Registry());
		model.addAttribute("employees", employeeService.findAll());
		return "create/form-registry";
	}
	
	@PostMapping("/new/submit")
	public String submitNewWorkDay(@ModelAttribute("registry") Registry registry, Model model) {
		String url = "list/list-registry";
		LocalDateTime dateRegistry = LocalDateTime.now();
		registry.setDateRegistry(dateRegistry );
		registryService.save(registry);
		
		List<Registry> registrys = new ArrayList<Registry>(registryService.findAll());

		model.addAttribute("registrys", registrys);

		return url;
	}
	
	@GetMapping("/edit/{id}")
	public String editRegistry(@PathVariable("id") Long id, Model model) {

		String url = "";
		Registry registry = registryService.findById(id);

		if (registry != null) {
			model.addAttribute("registry", registry);
			model.addAttribute("employees", employeeService.findAll());
			url = "create/form-registry";

		} else {
			url = "redirect:/create/registry/";
		}
		return url;
	}

	@GetMapping("/delete/{id}")
	public String deleteRegistry(@PathVariable("id") Long id, Model model) {

		String url = "";
		Registry workday = registryService.findById(id);

		if (workday != null) {
			registryService.delete(id);
			url = "redirect:/create/registry/";
		}
		return url;
	}

}
