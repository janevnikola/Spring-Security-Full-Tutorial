package com.nikola.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {//password configuration klassa

	@Bean
	public PasswordEncoder passwordEncoder() {//passwordEncoder funkcija
		
		return new BCryptPasswordEncoder(10);//koja vrakja tip na encoder BCryptPasswordEncoder so strength 10
	}
}
