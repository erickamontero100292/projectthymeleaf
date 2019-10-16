package com.workday.controller;

import com.workday.model.Employee;
import com.workday.model.Registry;
import com.workday.model.UserApp;
import com.workday.services.EmployeeService;
import com.workday.services.RegistryService;
import com.workday.services.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/create/registry")
public class RegistryController {
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RegistryService registryService;
	
	@Autowired
	private  UserAppService appService;
	
	private UserApp userApp;


    @ModelAttribute("registrys")
    public List<Registry> myRegistry() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userApp = appService.findFirstByUser(email);
        List<Registry> registrys;
        if(userApp.getRol().equalsIgnoreCase("ADMIN")){
            registrys = new ArrayList<Registry>(registryService.findAll());
        }else{
            Employee employee = employeeService.findByUser(userApp);
            registrys  = new ArrayList<Registry>(registryService.findByEmployee(employee));
        }

        return registrys;
    }

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

	@GetMapping("/listMyRegistry")
	public String listMyRegistry(Model model) {
		model.addAttribute("registrys");
		return "list/list-registry";
	}
	
	
	@GetMapping("/new")
	public String newemployee(Model model) {

		model.addAttribute("registry", new Registry());
		model.addAttribute("employees", employeeService.findAll());
		return "create/form-registry";
	}
	
	@PostMapping("/new/submit")
	public String submitNewWorkDay(@Valid Registry registry, BindingResult bindingResult, Model model) {
		String url = "list/list-registry";
		LocalDateTime dateRegistry = LocalDateTime.now();
		registry.setDateRegistry(dateRegistry );		
		
		List<Employee> employees = new ArrayList<Employee>(employeeService.findAll());
		if(registry.getHours() > 20) {
		bindingResult.rejectValue("hours", "error.registry", "No puede ser mayor a: "+ 20);
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("employees",employees);
			url = "create/form-registry";
		} else {
			registryService.save(registry);
			List<Registry> registrys = new ArrayList<Registry>(registryService.findAll());
			model.addAttribute("registrys", registrys);

		}
		
		
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
