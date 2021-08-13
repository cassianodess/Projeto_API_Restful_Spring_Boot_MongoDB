package com.cassianosouza.workshopmongo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cassianosouza.workshopmongo.domain.Post;
import com.cassianosouza.workshopmongo.repository.PostRepository;
import com.cassianosouza.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	/* Classe responsável por retornar demandas do Usuário em conjunto com */

	@Autowired // Instancia automaticamente um objeto.
	private PostRepository repo;

	public Post findById(String id) {
		Optional<Post> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
	}

	public List<Post> findByTitle(String txt) {
		return repo.searchTitle(txt);
	}

	public List<Post> fullSearch(String txt, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);

		return repo.fullSearch(txt, minDate, maxDate);
	}
}
