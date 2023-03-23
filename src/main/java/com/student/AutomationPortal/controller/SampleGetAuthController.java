package com.student.AutomationPortal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleGetAuthController {
    @GetMapping("/admin/test")
    public String admin(){
        return "<h1>Welcome to Admin Page<h1>";
    }
    @GetMapping("/v1/test")
    public String user(){
        return "<h1>Welcome to User Page<h1>";
    }
    @GetMapping("/all/test")
    public String all(){
        return "<h1>Welcome to global Page<h1>";
    }
}
