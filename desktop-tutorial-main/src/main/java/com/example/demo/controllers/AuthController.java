package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.*;
import com.example.demo.payload.request.*;
import com.example.demo.payload.response.*;
import com.example.demo.repository.*;
import com.example.demo.security.*;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.payload.*;

@CrossOrigin(origins = "https://angular1640.herokuapp.com", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	FalcutyRepository falcutyRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping(value = "/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles, userDetails.getAddress(), userDetails.getPhonenumber(),userDetails.getFalcuty_id()));
	}
	/*
	 * * Requests : - LoginRequest : {username,password} - SignupRequest :
	 * {username,email,password} *Responses : - JwtResponse :
	 * {token,type,id,username,email,roles} - Messageresponse : {message}
	 */

	@PostMapping(value = "/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		// TODO: process POST request
		
		 if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error : Username is already taken!"));
		}
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error : Email is already taken!"));
		}

		// Create new user's account
		
		//Falcuty falcuty = falcutyRepository.findById(i)
		//		.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));;
		//Falcuty falcuty_new = new Falcuty("FALCUTY_IB");
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()), signupRequest.getAddress(),
				signupRequest.getPhonenumber());
		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();
		//Set Falcuty
		Falcuty falcuty = new Falcuty();
		switch (signupRequest.getFalcuty_id()) {
		case "SE":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_SE)
					.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));
			break;
		case "AI":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_AI)
			.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));
			break;
		case "IB":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_IB)
			.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));
			break;
		case "SA":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_SA)
			.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));
			break;
		}
		user.setFalcuty(falcuty);
		// Set Roles
		if (strRoles == null || strRoles.isEmpty()) {
			Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
					.orElseThrow(() -> new RuntimeException("Error : Role is not found" + signupRequest.getRole()));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
					roles.add(adminRole);
					break;
				case "coordinator":
					Role coordinatorRole = roleRepository.findByName(ERole.ROLE_MKT_COORDINATOR)
							.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
					roles.add(coordinatorRole);
					break;
				case "manager":
					Role managerRole = roleRepository.findByName(ERole.ROLE_MKT_MANAGER)
							.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
					roles.add(managerRole);
					break;
				default:
					Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
							.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
					roles.add(studentRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		
	}

}
