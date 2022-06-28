package com.barbershop.user.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.barbershop.error.BarberShopException;
import com.barbershop.user.api.dto.UserCreateDto;
import com.barbershop.user.domain.model.User;
import com.barbershop.user.domain.repository.UserRepository;

@SpringJUnitConfig
class UserServiceTest {
	
	@Mock
	private UserRepository repository;
	
	@InjectMocks
	private UserService service;
	
	@Test
	@DisplayName("Error when creating a user with an already existing email")
	void errorWhenCreateUserWithExistingEmail() {
		UserCreateDto dto = new UserCreateDto();
		dto.setBirthDate(new Date());
		dto.setEmail("user.name@user.com");
		dto.setLastname("Name");
		dto.setName("User");
		
		doReturn(Optional.of(new User())).when(repository).findByEmail(dto.getEmail());
		
		BarberShopException exception = assertThrows(BarberShopException.class, () -> service.create(dto));
		
		assertThat(exception.getMessage()).isEqualTo(MessageFormat.format("Já existe um usuário cadastrado para o e-mail {0}.", dto.getEmail()));
	}
	
	@Test
	@DisplayName("On create fill in the nickname with the name when not informed")
	void onCreateFillNicknameWithNameWhenNotInfomed() {
		UserCreateDto dto = new UserCreateDto();
		dto.setBirthDate(new Date());
		dto.setEmail("user.name@user.com");
		dto.setLastname("Name");
		dto.setName("User");
		
		doReturn(Optional.empty()).when(repository).findByEmail(dto.getEmail());
		
		service.create(dto);
		
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		
		verify(repository).save(captor.capture());
		
		User user = captor.getValue();
		
		assertThat(user.getNickname()).isEqualTo(dto.getName());
	}
	
	@Test
	@DisplayName("On create keep nickname when informed")
	void onCreateKeepNicknameWhenInfomed() {
		UserCreateDto dto = new UserCreateDto();
		dto.setBirthDate(new Date());
		dto.setEmail("user.name@user.com");
		dto.setLastname("Name");
		dto.setName("User");
		dto.setNickname("Us");
		
		doReturn(Optional.empty()).when(repository).findByEmail(dto.getEmail());
		
		service.create(dto);
		
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		
		verify(repository).save(captor.capture());
		
		User user = captor.getValue();
		
		assertThat(user.getNickname()).isEqualTo(dto.getNickname());
	}
	
}
