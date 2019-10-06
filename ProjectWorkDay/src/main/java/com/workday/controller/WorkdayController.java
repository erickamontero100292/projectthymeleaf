package com.workday.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workday.model.Workday;
@Controller
@RequestMapping("/create/workday")
public class WorkdayController {
	
	@GetMapping("/new")
	public String nerWorkDay(Model model) {
		model.addAttribute("work",new Workday());
		return "create/form-workday";
	}

}
