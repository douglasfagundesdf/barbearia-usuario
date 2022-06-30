package com.barbershop.user.domain.service;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbershop.error.BarberShopException;
import com.barbershop.error.BarberShopNotFoundException;
import com.barbershop.user.domain.dto.UserCreateDto;
import com.barbershop.user.domain.dto.UserDto;
import com.barbershop.user.domain.dto.UserModifyDto;
import com.barbershop.user.domain.model.User;
import com.barbershop.user.domain.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public UserDto findById(Long id) {
		return findUserById(id)
				.map(user -> modelMapper.map(user, UserDto.class))
				.orElseThrow(() -> userNotFoundException(id));
	}
	
	public Long create(@Valid UserCreateDto dto) {
		User user = modelMapper.map(dto, User.class);
		
		validateExistingUser(user);
		
		fillNicknameWhenNotInformed(user);
		
		repository.save(user);
		
		return user.getId();
	}
	
	public void update(Long id, @Valid UserModifyDto dto) {
		User user = findUserById(id)
				.orElseThrow(() -> userNotFoundException(id));
		
		modelMapper.map(dto, user);
		
		repository.save(user);
	}
	
	public boolean delete(Long id) {
		Optional<User> user = findUserById(id);
		
		if (user.isPresent()) {
			repository.delete(user.get());
			
			return true;
		}
		
		return false;
	}
	
	private Optional<User> findUserById(Long id) {
		return repository.findById(id);
	}
	
	private void validateExistingUser(User user) {
		Optional<User> userWithEmail = repository.findByEmail(user.getEmail());
		
		if (userWithEmail.isPresent()) {
			throw new BarberShopException("Já existe um usuário cadastrado para o e-mail {0}.", user.getEmail());
		}
	}
	
	private void fillNicknameWhenNotInformed(User user) {
		if (user.getNickname() == null)
			user.setNickname(user.getName());
	}
	
	private BarberShopNotFoundException userNotFoundException(Long id) {
		return new BarberShopNotFoundException("User not found to id {0}", id);
	}
	
}
