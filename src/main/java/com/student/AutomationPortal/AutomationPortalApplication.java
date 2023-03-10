package com.student.AutomationPortal;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableWebSecurity
public class AutomationPortalApplication {
	Logger logger= Logger.getLogger(this.getClass().getName());
	public static void main(String[] args) {
		SpringApplication.run(AutomationPortalApplication.class, args);
	}

}
