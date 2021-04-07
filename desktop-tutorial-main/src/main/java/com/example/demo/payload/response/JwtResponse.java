package com.example.demo.payload.response;

import java.util.List;

import com.example.demo.models.Falcuty;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	private String address;
	private String phonenumber;
	private Integer falcuty_id;

	public JwtResponse(String accessToken, Long id, String username, 
			String email, List<String> roles,String address,String phonenumber,Integer falcuty_id) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.address = address;
		this.phonenumber = phonenumber;
		this.falcuty_id = falcuty_id;
	
	}
	
	
	public Integer getFalcuty_id() {
		return falcuty_id;
	}


	public void setFalcuty_id(Integer falcuty_id) {
		this.falcuty_id = falcuty_id;
	}


	public String getAccessToken() {
		return token;
	}
	public void setAccessToken(String token) {
		this.token = token;
	}
	public String getTokenType() {
		return type;
	}
	public void setTokenType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
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
	
	
	
}
