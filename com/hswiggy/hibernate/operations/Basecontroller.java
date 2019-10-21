package com.hswiggy.hibernate.operations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Basecontroller {

	
	@RequestMapping(value="/goToHomePage")
	public String back() {
		System.out.println("executing BaseController::goToHome method");
		
		return "menu";
	}
}
