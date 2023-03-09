package com.student.AutomationPortal.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationController {

	@GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "login";
    }
}
