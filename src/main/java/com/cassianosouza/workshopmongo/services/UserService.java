package com.cassianosouza.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cassianosouza.workshopmongo.domain.User;
import com.cassianosouza.workshopmongo.dto.UserDTO;
import com.cassianosouza.workshopmongo.repository.UserRepository;
import com.cassianosouza.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	/* Classe responsável por retornar demandas do Usuário em conjunto com */

	@Autowired // Instancia automaticamente um objeto.
	private UserRepository userRepository;

	// Retorna todos os usuários do banco de dados
	public List<User> findAll() {
		return userRepository.findAll();

	}

	public User findById(String id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
	}

	public User insert(User obj) {
		return userRepository.insert(obj);

	}

	public void delete(String id) {
		findById(id);
		userRepository.deleteById(id);
	}

	public User update(User obj) {
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return userRepository.save(newObj);

	}

	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());

	}

	public User fromDTO(UserDTO obj) {
		return new User(obj.getId(), obj.getName(), obj.getEmail());

	}

}
