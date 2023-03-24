package com.student.AutomationPortal.controller.ui;

import com.student.AutomationPortal.config.UserDetailsServiceImpl;
import com.student.AutomationPortal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ApplicationController {

//	@GetMapping("/")
//    public String home(Model model) {
//        model.addAttribute("message", "Hello, Thymeleaf!");
//        return "login";
//    }
/*
    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login";
    }
*/
@Autowired
private UserDetailsServiceImpl userService;

    @GetMapping("/")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/home")
    public String home() {
        return "homePage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login?logout";
    }

}

