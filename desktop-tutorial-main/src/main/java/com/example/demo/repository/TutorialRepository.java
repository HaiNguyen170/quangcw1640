package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long>{
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);
	
	/* - Now we can use JpaRepository's method : save()
	 * findOne(),findById(),findAll(),count(),delete(),deleteById()..
	 * without implementing these methods
	 * - We also define custom finder methods:
	 * 		+ findByPublished():returns all tutorials with published
	 * having value as input published
	 * 		+ findByTitleContaining(): returns all Tutorials which title
	 * contains input title*/
}
