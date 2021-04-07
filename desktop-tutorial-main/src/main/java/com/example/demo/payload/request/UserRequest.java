package com.example.demo.payload.request;

import javax.validation.constraints.*;
import com.example.demo.models.*;

public class UserRequest {
	
	@NotBlank
	@Size(max=100)
	private String username;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@NotBlank
	@Size(max=100)
	private String address;
	
	@NotBlank
	@Size(max=11)
	private String phonenumber;
	
	@NotBlank
	@Size(max=100)
	private String falcuty;
	


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getFalcuty() { 
		return falcuty;
	}

	public void setFalcuty(String falcuty) {
		this.falcuty = falcuty;
	}




}
