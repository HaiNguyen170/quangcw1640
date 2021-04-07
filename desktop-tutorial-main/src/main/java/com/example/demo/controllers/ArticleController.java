package com.example.demo.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Article;
import com.example.demo.models.EFalcuty;
import com.example.demo.models.Falcuty;
import com.example.demo.models.User;
import com.example.demo.payload.request.ArticleRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.FalcutyRepository;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ArticleController {
	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	FalcutyRepository falcutyRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping(value = "/articles")
	public ResponseEntity<List<Article>> getAllArticle(@RequestParam(required = false) String title) {
		try {
			List<Article> articles = new ArrayList<Article>();
			if (title == null)
				articleRepository.findAll().forEach(articles::add);
			else
				articleRepository.findByTitleContaining(title).forEach(articles::add);
			if (articles.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(articles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			// TODO: handle exception
		}
	}

	@GetMapping(value = "/articles/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable("id") long id) {
		Optional<Article> articleData = articleRepository.findById(id);
		if (articleData.isPresent())
			return new ResponseEntity<>(articleData.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/articles")
	public ResponseEntity<MessageResponse> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
		Article article = new Article();
		java.util.Date now = new Date();
		article.setExpired_date(now);
		article.setCreated_date(now);
		article.setTitle(articleRequest.getTitle());
		Date created_date = new Date();
		Date expired_date = new Date();
		try {
			created_date = new SimpleDateFormat("yyyy-MM-dd").parse(articleRequest.getCreated_date());
			expired_date = new SimpleDateFormat("yyyy-MM-dd").parse(articleRequest.getExpired_date());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.ok(new MessageResponse("catch u"));
		}
		boolean active = true;
		if (articleRequest.getActive().equals("false"))
			active = false;
		article.setActive(active);
		// Article article = new Article(articleRequest.getTitle(),now,now,active);
		// Set User
		// long num = Long.parseLong();
		User u = userRepository.findById(Long.parseLong(articleRequest.getUserid()))
				.orElseThrow(() -> new RuntimeException("Error : User is not found"));
		article.setUser(u);
		// Set Falcuty
		Falcuty falcuty = new Falcuty();
		switch (articleRequest.getFalcuty()) {
		case "SE":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_SE)
					.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));
			break;
		case "AI":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_AI)
					.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));
			break;
		case "IB":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_IB)
					.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));
			break;
		case "SA":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_SA)
					.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));
			break;
		}
		article.setFalcuty(falcuty);
		articleRepository.save(article);
		return ResponseEntity.ok(new MessageResponse("Article created successfully!" + article.getTitle() + " "
				+ article.getCreated_date() + " " + article.getExpired_date()));

	}

	@PutMapping(value = "/articles/{id}")
	public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody Article article) {

		Optional<Article> articleData = articleRepository.findById(id);
		if (articleData.isPresent()) {
			Article _article = articleData.get();
			_article.setTitle(article.getTitle());
			_article.setActive(article.isActive());
			_article.setCreated_date(article.getCreated_date());
			_article.setExpired_date(article.getExpired_date());
			_article.setFalcuty(article.getFalcuty());
			_article.setUser(article.getUser());
			return new ResponseEntity<Article>(articleRepository.save(_article), HttpStatus.OK);
		}

		return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/articles/{id}")
	public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") long id) {
		// TODO: process DELETE request
		try {
			articleRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
