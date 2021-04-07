package com.example.demo.payload.request;

import javax.validation.constraints.*;
import com.example.demo.models.Article;

public class ArticleRequest {
	@NotBlank
	@Size(max=100)
	private String title;
	
	@NotBlank
	@Size(max=100)
	private String created_date;
	
	@NotBlank 
	@Size(max=100)
	private String expired_date;
	
	@NotBlank
	@Size(max=100)
	private String active;
	
	@NotBlank
	@Size(max=100)
	private String falcuty;
	
	@NotBlank 
	@Size(max=100)
	private String userid;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getExpired_date() {
		return expired_date;
	}

	public void setExpired_date(String expired_date) {
		this.expired_date = expired_date;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getFalcuty() {
		return falcuty;
	}

	public void setFalcuty(String falctuty) {
		this.falcuty = falctuty;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	
}
