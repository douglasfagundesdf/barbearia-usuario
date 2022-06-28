package com.barbershop.user.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.barbershop.user.domain.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
}
