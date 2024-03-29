package com.student.AutomationPortal;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//http://localhost:8080/swagger-ui.html
@SpringBootApplication
@EnableSwagger2
public class AutomationPortalApplication {
	Logger logger= Logger.getLogger(this.getClass().getName());
	public static void main(String[] args) {
		SpringApplication.run(AutomationPortalApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.student.AutomationPortal")).build();
	}
}
