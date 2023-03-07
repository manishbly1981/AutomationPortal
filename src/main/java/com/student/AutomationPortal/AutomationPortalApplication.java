package com.student.AutomationPortal;

import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableWebSecurity
public class AutomationPortalApplication {
	Logger logger= Logger.getLogger(this.getClass().getName());
	public static void main(String[] args) {
		SpringApplication.run(AutomationPortalApplication.class, args);
	}

}
