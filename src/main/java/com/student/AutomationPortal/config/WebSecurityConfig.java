package com.student.AutomationPortal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	public void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
//		http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
		http.authorizeRequests().antMatchers("/api/v1/auth/**").fullyAuthenticated().and().httpBasic();
/*		http.authorizeRequests().antMatchers("/api/admin/**")
		.hasAnyRole("admin").anyRequest().fullyAuthenticated()
		.and().httpBasic();
*/	
		
		//unlocking functionality and security configuration
	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("manish").password("manish").roles("admin");
		auth.inMemoryAuthentication().withUser("arti").password("arti").roles("user");
		
		//Check the login attempt and lock the account if needed
	}

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}
