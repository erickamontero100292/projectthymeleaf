package com.workday.controller;


import com.workday.entity.EntityUserApp;
import com.workday.services.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String url = "login";
        try {
            if (((UserDetails) principal).isEnabled()) {
                url = "redirect:/";
            }
        } catch (ClassCastException e) {
            model.addAttribute("userApp", new EntityUserApp());
        }
        return url;
    }


    @PostMapping("/auth/register")
    public String register(@ModelAttribute EntityUserApp userApp) {

        userAppService.save(userApp);
        return "redirect:/auth/login";
    }

}
