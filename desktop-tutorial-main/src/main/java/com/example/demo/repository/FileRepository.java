package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.*;


@Repository
public interface FileRepository extends JpaRepository<File, String>{
	
	/*-Now we can use FileRepository with JpaRepository's method such as : save(File)
	 * ,findById(id),findAll()*/
	List<File> findByArticle_id(long article_id);
	
	// Tìm theo danh sách các file đã được publish
	List<File> findByPublished(boolean published);
	
	// Tìm theo người tạo ra file
	List<File> findByUser(long user);
	// Khi tạo JPA find... thì find không viết hoa chữ đầu , sau by
	// là attribute trong model viết hoa chữ cái đầu
}
