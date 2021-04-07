package com.example.demo.payload.request;

import java.util.Set;
import javax.validation.constraints.*;

import com.example.demo.models.Falcuty;

public class SignupRequest {
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	
	@NotBlank
	@Size(max=120)
	private String address;
	
	@NotBlank
	@Size(max=11)
	private String phonenumber;
	
	@NotBlank
	@Size(max=11)
	private String falcuty_id;
	
	public String getFalcuty_id() {
		return falcuty_id;
	}

	public void setFalcuty_id(String falcuty_id) {
		this.falcuty_id = falcuty_id;
	}

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

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	

}
