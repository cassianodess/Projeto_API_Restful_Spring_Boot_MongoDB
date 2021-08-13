package com.cassianosouza.workshopmongo.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cassianosouza.workshopmongo.domain.Post;
import com.cassianosouza.workshopmongo.resources.util.URL;
import com.cassianosouza.workshopmongo.services.PostService;

@RestController // Indica que essa classe é um recurso rest REST (DAO)
@RequestMapping(value = "/posts") // Indica o end-point (URL)
public class PostResource {

	/*
	 * Essa classe é responsável pelo output das requisições de posts do usuário,
	 * enquanto UserService implementa o que é preciso, como uma class DAO..
	 */

	@Autowired
	private PostService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post post = service.findById(id);
		return ResponseEntity.ok().body(post);

	}

	@GetMapping(value = "/titlesearch")
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {

		text = URL.decodeParam(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/fullsearch")
	public ResponseEntity<List<Post>> fullSearch(@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) {

		text = URL.decodeParam(text);
		Date min = URL.converteDate(minDate, new Date(0L));
		Date max = URL.converteDate(maxDate, new Date());

		List<Post> list = service.fullSearch(text, min, max);
		return ResponseEntity.ok().body(list);
	}

}
