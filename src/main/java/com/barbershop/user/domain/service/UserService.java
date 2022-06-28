package com.barbershop.user.domain.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbershop.error.BarberShopException;
import com.barbershop.user.api.dto.UserCreateDto;
import com.barbershop.user.api.dto.UserDto;
import com.barbershop.user.api.dto.UserModifyDto;
import com.barbershop.user.domain.model.User;
import com.barbershop.user.domain.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public Optional<UserDto> findById(Long id) {
		return findUserById(id)
				.map(user -> modelMapper.map(user, UserDto.class));
	}
	
	public Long create(UserCreateDto dto) {
		User user = modelMapper.map(dto, User.class);
		
		validateExistenteUser(user);
		
		if (user.getNickname() == null)
			user.setNickname(user.getName());
		
		repository.save(user);
		
		return user.getId();
	}
	
	public boolean update(Long id, UserModifyDto dto) {
		Optional<User> optionalUser = findUserById(id);
		
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			modelMapper.map(dto, user);
			
			repository.save(user);
			
			return true;
		}
		
		return false;
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
	
	private void validateExistenteUser(User user) {
		Optional<User> userWithEmail = repository.findByEmail(user.getEmail());
		
		if (userWithEmail.isPresent()) {
			throw new BarberShopException("Já existe um usuário cadastrado para o e-mail {0}.", user.getEmail());
		}
	}
	
}
