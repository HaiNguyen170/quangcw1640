package com.example.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://angular1640.herokuapp.com" , maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@GetMapping(value = "/all")
	public String allAccess() {
		return "Public Content";
	}
	
	@GetMapping(value = "/student")
	@PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('COORDINATOR') or hasRole('MANAGER') or hasRole('ADMIN')")
	public String studentAccess() {
		return "Student Content";
	}
	
	@GetMapping(value = "/coordinator")
	@PreAuthorize("hasRole('ROLE_MKT_COORDINATOR')")
	public String coordiantorAccess() {
		return "Coordinator Content";
	}
	
	@GetMapping(value = "/manager")
	@PreAuthorize("hasRole('ROLE_MKT_MANAGER')")
	public String managerAccess() {
		return "Manager Content";
	}
	
	@GetMapping(value = "/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String getMethodName() {
		return "Admin Content";
	}




}
