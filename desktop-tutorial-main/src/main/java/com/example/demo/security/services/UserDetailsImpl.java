package com.example.demo.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String username;
	
	private String email;
	
	private String address;
	
	private String phonenumber;
	
	@JsonIgnore
	private String password;
	
	private Integer falcuty_id;
	
	public Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(Long id,String username,String email,String password,
			Collection<? extends GrantedAuthority> authorities,String address,String phonenumber,Integer falcuty_id) {
		this.id=id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.address = address;
		this.phonenumber = phonenumber;
		this.falcuty_id = falcuty_id;
	}
	
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				authorities,
				user.getAddress(),
				user.getPhonenumber(),
				user.getFalcuty().getId()
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	

	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getFalcuty_id() {
		return falcuty_id;
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

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass()!= o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
	
	
	/*
	 * -Look at the code above , you can notice that we convert
	 * Set<Role> into List<GrantedAuthority>. It is important to 
	 * work with Spring Security and Authentication object later.
	 * - As I have said before ,  we need UserDetailsService for getting 
	 * UserDetails object . You can look at UserDetailsService interface
	 * that has only one method:
	 * - Public interface UserDetailsService{
	 * 		UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	 * {*/

}
