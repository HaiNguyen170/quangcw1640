package com.example.demo.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.*;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findByTitleContaining(String title);
	List<Article> findByActive(boolean active);
	Boolean existsByTitle(String title);
}
