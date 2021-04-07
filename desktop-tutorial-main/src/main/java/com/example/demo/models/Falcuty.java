package com.example.demo.models;

import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "falcuty")
public class Falcuty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EFalcuty name;

	@OneToMany(mappedBy = "falcuty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> user;
	
	@OneToMany(mappedBy = "falcuty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Article> article ;

	public Falcuty(EFalcuty name) {
		super();
		this.name = name;
	}

	// Getters and setters omitted for brevity

	public Falcuty() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EFalcuty getName() {
		return name;
	}

	public void setName(EFalcuty name) {
		this.name = name;
	}
	/*
	 *	INSERT INTO falcuty(name) VALUE ("FALCUTY_SE");
		INSERT INTO falcuty(name) VALUE ("FALCUTY_IB");
		INSERT INTO falcuty(name) VALUE ("FALCUTY_AI");
		INSERT INTO falcuty(name) VALUE ("FALCUTY_SA");
*/

}
