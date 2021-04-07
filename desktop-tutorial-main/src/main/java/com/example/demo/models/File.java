package com.example.demo.models;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "files")
public class File {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String name;

	private String type;
	
	private boolean published;

	@Lob
	private byte[] data;

	public File() {

	}

	public File(String name, String type, byte[] data,boolean published) {
		this.name = name;
		this.type = type;
		this.data = data;
		this.published = published;
	}

	// Many files have only one article
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "creator_id", nullable = false)
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	/*
	 * -File is the data model corresponding to files table in database.
	 * -FileRepository extends Spring Data JpaRepository which has methods to store
	 * and retrieve files. -FilesStorageService uses FileRepository to provide
	 * methods for saving new file, get file by id , get list of Files.
	 * -FilesController uses FilesStorageService to export Rest APIs : POST a file ,
	 * GET all files's information, download file -FileUploadExceptionAdvice handles
	 * exception when the controller preocesses file upload . -ResponseFile contains
	 * configuration for Servlet Multipart and MySQL database connection
	 */
	

}
