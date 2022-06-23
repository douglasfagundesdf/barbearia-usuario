package br.com.barbearia.usuario.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.barbearia.usuario.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
}
