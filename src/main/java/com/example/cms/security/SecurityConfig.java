package com.example.cms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	// bcript which is used to encrypt any password more secure way hashing;
	//
	private CustomUserDetailService userDetailService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}
	
	
	@Bean
	SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception
	{
	return	 http.csrf(csrf->csrf.disable())
		 .authorizeHttpRequests(auth->auth.requestMatchers("/users/register")
		 .permitAll().anyRequest().authenticated())
		   .formLogin(Customizer.withDefaults()).build();
	}
	

	

	@Bean
	AuthenticationProvider authenticationProvider()
	{

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailService);
		return provider;
	}

}
