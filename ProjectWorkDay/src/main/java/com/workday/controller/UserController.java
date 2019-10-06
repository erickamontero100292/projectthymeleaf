package com.workday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workday.model.User;

@Controller
@RequestMapping("/create/user")
public class UserController {

	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("user", new User());
		return "create/form-user";
	}
	
	@GetMapping("/new")
	public String newUser(Model model) {
		
		model.addAttribute("user", new User());
		return "create/form-user";
	}
	
}
