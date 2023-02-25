package com.nikola.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration //klasata mora da bide anotirana so @Configuration bidejki e configuration klasa
@EnableWebSecurity //so anotiranje na @EnableWebSecurity kazuvame deka vo ovaa klasa ke implementirame se
//za security za nasata aplikacija
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder=passwordEncoder;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception	{
		
		http
		.csrf().disable() //enable go posle
		.authorizeHttpRequests()
		.shouldFilterAllDispatcherTypes(false)
		.requestMatchers("/").permitAll()
		.requestMatchers("index").permitAll()
		.requestMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())//ovoj request matcher ke go zastiti ovoj endpoint i ke dozvoli pristap za site so imaat role student
//		.requestMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
	//	.requestMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//		.requestMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
	//	.requestMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())
		.anyRequest()//sekoj request 											
		
		.authenticated()//mora da bide avtenticiran 
		.and()//i
		.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/courses",true)
			.usernameParameter("username")
			.passwordParameter("password")
		.and()
		.rememberMe() //default 2 nedeli
			.rememberMeParameter("remember-me")
		.and()
		.logout()
			.logoutUrl("/logout")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID","remember-me")
			.logoutSuccessUrl("/login");
		
		//.httpBasic();//mehanizmot koi sakame da go koristime e http Basic
		return http.build();
	}
	
	@Bean
	protected UserDetailsService userDetailsService(){//inMemoryUser
	UserDetails nikolajanevUser=User.builder()//koristime userBuilder za kreiranje na user
		.username("nikolajanev")//username
		.password(passwordEncoder.encode("password"))//password so encoder
		//.roles(ApplicationUserRole.STUDENT.name())//role koj ke go ima ROLE_STUDENT
		.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
		.build();//go buildame
	
	
	//user so ROLE ADMIN	
	UserDetails lindaUser =User.builder()
	.username("linda")
	.password(passwordEncoder.encode("password123"))
	//.roles(ApplicationUserRole.ADMIN.name())
	.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
	.build();
		
	
	//user so ROLE ADMIN	
		UserDetails tomUser =User.builder()
		.username("tom")
		.password(passwordEncoder.encode("password123"))
		//.roles(ApplicationUserRole.ADMINTRAINEE.name())
		.authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
		.build();
	
	return new InMemoryUserDetailsManager(nikolajanevUser,lindaUser,tomUser);//ova ke go kreira userot
	}
	
}
