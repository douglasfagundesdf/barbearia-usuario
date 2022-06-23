package br.com.barbearia.usuario.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barbearia.usuario.api.dto.UserDto;
import br.com.barbearia.usuario.api.dto.UserIncomingDto;
import br.com.barbearia.usuario.model.User;
import br.com.barbearia.usuario.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public Long create(UserIncomingDto userIncomingDto) {
		User user = modelMapper.map(userIncomingDto, User.class);
		
		if (user.getNickname() == null)
			user.setNickname(user.getName());
		
		repository.save(user);
		
		return user.getId();
	}
	
	public Optional<UserDto> findById(Long id) {
		return findUserById(id)
				.map(user -> modelMapper.map(user, UserDto.class));
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
	
}
