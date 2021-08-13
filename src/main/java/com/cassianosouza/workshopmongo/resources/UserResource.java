package com.cassianosouza.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cassianosouza.workshopmongo.domain.Post;
import com.cassianosouza.workshopmongo.domain.User;
import com.cassianosouza.workshopmongo.dto.UserDTO;
import com.cassianosouza.workshopmongo.services.UserService;

@RestController // Indica que essa classe é um recurso rest REST (DAO)
@RequestMapping(value = "/users") // Indica o end-point (URL)
public class UserResource {

	/*
	 *
	 * Essa classe é responsável pelo output das requisições do usuário, enquanto
	 * UserService implementa o que é preciso, como uma class DAO..
	 * 
	 * Método responsável por retornar a requisição de findAll() retorna um
	 * ResponseEntity, pois essa classe encapsula já com possíveis cabeçalhos,
	 * erros, etc.
	 */

	@Autowired
	private UserService userService;

	@GetMapping // Metodo para obter informações no REST
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = userService.findAll();
		List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);

		/*
		 * Ok - indica que instancia já com o código de resposat HTTP de sucesso. body -
		 * Define o corpo da resposta.
		 */

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User user = userService.findById(id);
		return ResponseEntity.ok().body(new UserDTO(user));

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();

	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDTO) {
		User user = userService.fromDTO(objDTO);
		user = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody UserDTO objDTO, @PathVariable String id) {
		User user = userService.fromDTO(objDTO);
		user.setId(id);
		user = userService.update(user);
		return ResponseEntity.noContent().build();

	}

	@GetMapping(value = "/{id}/posts")
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
		User user = userService.findById(id);
		return ResponseEntity.ok().body(user.getPosts());

	}

}
