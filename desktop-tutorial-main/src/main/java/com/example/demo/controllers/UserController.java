package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.*;
import com.example.demo.payload.request.UserRequest;
import com.example.demo.repository.*;


@CrossOrigin(origins="https://angular1640.herokuapp.com/")
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	FalcutyRepository falcutyRepository;
	
	@Autowired
	UserRepository userRepository;
	
	//Done
	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getAllUser(){
		try {
			List<User> users = new ArrayList<>();
			userRepository.findAll().forEach(users ::add);
			return new ResponseEntity<>(users,HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Done 
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") long id){
		 Optional<User> user = userRepository.findById(id);
		 if(user.isPresent()) return new ResponseEntity<>(user.get(),HttpStatus.OK);
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	//Done
	@PutMapping(value = "/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody UserRequest userreq) {
		
		Optional<User> userData= userRepository.findById(id);
		Falcuty falcuty = new Falcuty();
		switch (userreq.getFalcuty()) {
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
		
		if(userData.isPresent()) {
			User _user = userData.get();
			_user.setUsername(userreq.getUsername());
			_user.setAddress(userreq.getAddress());
			_user.setEmail(userreq.getEmail());
			_user.setPhonenumber(userreq.getPhonenumber());
			_user.setFalcuty(falcuty);
			return new ResponseEntity<>(userRepository.save(_user),HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Done
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
		//TODO: process DELETE request
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
