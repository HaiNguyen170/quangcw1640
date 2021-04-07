package com.example.demo.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@NotBlank
	@Size(max = 120)
	private String address;

	@NotBlank
	@Size(max = 11)
	private String phonenumber;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	// Many students have only one falcuty
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "falcuty_id", nullable = false)
	private Falcuty falcuty;

	// One users can create many articles
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Article> article;

	// One users can create many files
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<File> file;

	public User() {
	}

	/*
	 * public User(String username, String email,String password, String
	 * address,String phonenumber) { super(); this.username = username; this.email =
	 * email; this.password = password; this.address = address; this.phonenumber =
	 * phonenumber;
	 * 
	 * }
	 */
	public User(String username, String email, String password, String address, String phonenumber) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.address = address;
		this.phonenumber = phonenumber;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public Falcuty getFalcuty() {
		return falcuty;
	}

	public void setFalcuty(Falcuty falcuty) {
		this.falcuty = falcuty;
	}

}
