package com.workday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@GetMapping({"/", "/welcome"})
	public String welcome(@RequestParam(name="name", required=false, defaultValue="Mundo") String name, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("saludo", "Un saludo para todos los programadores del mundo");
		model.addAttribute("boton", "Ir a...");
		return "index";
	}

}
