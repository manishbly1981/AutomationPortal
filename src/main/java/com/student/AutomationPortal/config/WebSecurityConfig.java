
package com.student.AutomationPortal.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
//		http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
		http.authorizeRequests().antMatchers("/js/**", "/css/**").permitAll()
		/*
		 * .anyRequest().authenticated() .and() .formLogin() .loginPage("/index")
		 * .permitAll() .and() .logout() .permitAll()
		 */
		;
		//http.authorizeRequests().antMatchers("/api/v1/auth/**").fullyAuthenticated().and().httpBasic();
		
		 http.authorizeRequests().antMatchers("/api/v1/auth/**")
		 .hasAnyRole("admin").anyRequest().fullyAuthenticated() .and().httpBasic();
		 

		// unlocking functionality and security configuration
	}

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.inMemoryAuthentication().withUser("manish").password("manish").roles("admin");
		 auth.inMemoryAuthentication().withUser("arti").password("arti").roles("user");

		/*
		auth.jdbcAuthentication()//.passwordEncoder(new AttributeEncrypter())
		.dataSource(dataSource)
		.usersByUsernameQuery("select email, password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select username, role from users where username=?");
		*/
	}

	

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}
