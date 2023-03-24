
package com.student.AutomationPortal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String ADMIN="ADMIN";
	private static final String USER="USER";

	@Autowired
	private UserDetailsService userDetailsService;

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
		public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/admin/**").hasRole(ADMIN)
				.antMatchers("/api/v1/**").hasAnyRole(ADMIN, USER)
				.antMatchers("/api/register").permitAll()
				.antMatchers("/api/activateUser").permitAll()
				.antMatchers("/api/login").permitAll()
				.antMatchers("/api/inlockUser").permitAll()
				.antMatchers("/api/all/test").permitAll()
				.and().httpBasic()
				.and().formLogin()
				.loginPage("/")
				.defaultSuccessUrl("/home", true)
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
//				.and()
//				.headers()
//				.frameOptions()
//				.sameOrigin()
		;


	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Allow accessing static resources without authentication
		web.ignoring().antMatchers("/styles/**", "/js/**","images/**");
	}

	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return NoOpPasswordEncoder.getInstance();
	}
}
