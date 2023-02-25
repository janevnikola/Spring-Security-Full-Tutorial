package com.nikola.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

	@GetMapping("/login")
	public String getLoginView() {
		return "login";
	}
	
	@GetMapping("/courses")
	public String getCoursesView() {
		return "courses";
	}
	
	
}
