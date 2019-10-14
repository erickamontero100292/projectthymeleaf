package com.workday.controller;


import com.workday.model.UserApp;
import com.workday.services.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    UserAppService userAppService;

    @GetMapping("/")
    public String welcome() {
        return "redirect:/public/";
    }


    @GetMapping("/auth/login")
    public String login(Model model) {
        model.addAttribute("userApp", new UserApp());
        return "login";
    }


    @PostMapping("/auth/register")
    public String register(@ModelAttribute UserApp userApp) {

        userAppService.add(userApp);
        return "redirect:/auth/login";
    }

}