package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query(value = "SELECT * FROM users WHERE falcuty_id = ?1", nativeQuery = true)
	List<User> findByReviewId(long id);
	
	@Query(value = "UPDATE users\r\n"
			+ "	SET username = ?2, email = ?1, address = ?3, phonenumber = ?4, falcuty_id = ?5 \r\n"
			+ "	WHERE id = ?6;", nativeQuery = true)
	  void updateUser(String email,String username,String address,String phonenumber,int falcuty_id,long user_id);
	
	//
	
	
	List<User> findByFalcuty(Falcuty falcuty);
}
