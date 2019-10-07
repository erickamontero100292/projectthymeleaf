package com.workday.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workday.model.Workday;
import com.workday.services.WorkDayService;

@Controller
@RequestMapping("/create/workday")
public class WorkdayController {

	@Autowired
	private WorkDayService workdayService;

	@GetMapping("/new")
	public String nerWorkDay(Model model) {
		model.addAttribute("work", new Workday());
		return "create/form-workday";
	}

	@PostMapping("/new/submit")
	public String submitNewWorkDay(@ModelAttribute("workday") Workday workday, Model model) {

		workdayService.save(workday);
		List<Workday> workDays = new ArrayList<Workday>(workdayService.findAll());
		model.addAttribute("workdays",workDays);
		
		return "list/list-workday";
	}

	@GetMapping("/list")
	public String listWorkDay(Model model) {
		List<Workday> workDays = new ArrayList<Workday>(workdayService.findAll());
		model.addAttribute("workdays",workDays);
		return "list/list-workday";
	}

}
