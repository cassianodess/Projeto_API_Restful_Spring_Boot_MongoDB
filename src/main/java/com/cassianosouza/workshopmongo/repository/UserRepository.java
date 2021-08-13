package com.cassianosouza.workshopmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cassianosouza.workshopmongo.domain.User;

/*Interface responsável por fazer operações (já imbutidas) no banco de dados*/

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
