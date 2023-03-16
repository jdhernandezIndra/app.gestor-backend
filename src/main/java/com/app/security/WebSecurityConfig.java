package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {
	@Autowired
	private UserDetailsService userdetailsservices;
	@Autowired
	private JWTAuthorizationFilter jwtauthorizationfilter;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
		JWTAuthenticatorFilter jwtauthenticationfilter = new JWTAuthenticatorFilter();

		jwtauthenticationfilter.setAuthenticationManager(authManager);
		jwtauthenticationfilter.setFilterProcessesUrl("/token");

		return http.cors().and().csrf().disable().authorizeHttpRequests().requestMatchers("/usuarios/guardar")
				.permitAll().requestMatchers("/media/visualizar/**").permitAll().anyRequest().authenticated().and()
				.httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilter(jwtauthenticationfilter)
				.addFilterBefore(jwtauthorizationfilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	AuthenticationManager authMananger(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userdetailsservices)
				.passwordEncoder(passwordEncoder()).and().build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	public static void main(String[] args) {
//		System.err.println(new BCryptPasswordEncoder().encode("Admin2023*"));
//	}

}
