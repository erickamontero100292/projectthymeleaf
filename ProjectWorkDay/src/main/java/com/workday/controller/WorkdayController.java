package com.workday.controller;

import java.util.ArrayList;
import java.util.List;

import com.workday.services.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workday.configuration.PropertiesConfiguration;
import com.workday.entitty.EntityWorkday;
import com.workday.services.WorkDayService;

@Controller
@RequestMapping("/create/workday")
public class WorkdayController {

	@Autowired
	private WorkDayService workdayService;

	@Autowired
	private PropertiesConfiguration properties;

	@Autowired
	private I18nService i18nService;

	@GetMapping("/")
	public String index(Model model) {
		List<EntityWorkday> workDays = new ArrayList<EntityWorkday>(workdayService.findAll());
		model.addAttribute("workdays", workDays);
		return "list/list-workday";
	}

	@GetMapping("/new")
	public String nerWorkDay(Model model) {
		model.addAttribute("work", new EntityWorkday());
		return "create/form-workday";
	}

	@PostMapping("/new/submit")
	public String submitNewWorkDay(@ModelAttribute("workday") EntityWorkday workday, Model model) {
		long numberHourWeek = (properties.getDaysWeek() * workday.getNumberDailyHour());
		workday.setNumberWeekHour(numberHourWeek);
		workdayService.save(workday);
		List<EntityWorkday> workDays = new ArrayList<EntityWorkday>(workdayService.findAll());
		model.addAttribute("workdays", workDays);

		return "list/list-workday";
	}

	@GetMapping("/list")
	public String listWorkDay(Model model) {
		List<EntityWorkday> workDays = new ArrayList<EntityWorkday>(workdayService.findAll());
		model.addAttribute("workdays", workDays);
		return "list/list-workday";
	}

	@GetMapping("/edit/{id}")
	public String editWorkDay(@PathVariable("id") Long id, Model model) {

		String url = "";
		EntityWorkday workday = workdayService.findById(id);

		if (workday != null) {
			model.addAttribute("work", workday);
			url = "create/form-workday";

		} else {
			url = "redirect:/create/workday/";
		}
		return url;
	}

	@GetMapping("/delete/{id}")
	public String deleteWorkDay(@PathVariable("id") Long id, Model model) {

		String url = "";
		EntityWorkday workday = workdayService.findById(id);

		if (workday != null) {
			workdayService.delete(id);
			url = "redirect:/create/workday/";
		}
		return url;
	}
	@GetMapping("/delete/show/{id}")
	public String showModalDeleteEmployee(@PathVariable("id") Long id, Model model) {

		EntityWorkday workday = workdayService.findById(id);
		String deleteMessage = "";
		if (workday != null)
			deleteMessage = i18nService.getMessage("workday.delete.message", new Object[]{workday.getName()});
		else
			return "redirect:/admin/producto/?error=true";

		model.addAttribute("delete_url", "/create/workday/delete/" + id);
		model.addAttribute("delete_title", i18nService.getMessage("label.deleteWorkday"));
		model.addAttribute("delete_message", deleteMessage);
		return "fragments/modal::modal_delete";
	}

}
