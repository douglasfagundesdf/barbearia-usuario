package com.barbershop.user.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.barbershop.user.domain.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
}
