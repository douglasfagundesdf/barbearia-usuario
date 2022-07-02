package com.barbershop.user.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.barbershop.error.BarberShopException;
import com.barbershop.error.BarberShopNotFoundException;
import com.barbershop.user.domain.dto.UserCreateDto;
import com.barbershop.user.domain.dto.UserDto;
import com.barbershop.user.domain.dto.UserModifyDto;
import com.barbershop.user.domain.model.User;
import com.barbershop.user.domain.repository.UserRepository;

@SpringJUnitConfig
//@Configuration
//@ImportAutoConfiguration(ValidationAutoConfiguration.class)
//@ContextConfiguration(classes =  ValidationAutoConfiguration.class)
class UserServiceTest {
	
	@Mock
	private UserRepository repository;
	
	@InjectMocks
	private UserService service;
	
	@Test
	@DisplayName("Error when getting a non-existent user")
	void errorWhenGetNonExistentUser() {
		Long userId = 14L;
		
		doReturn(Optional.empty()).when(repository).findById(userId);
		
		assertThrows(BarberShopNotFoundException.class, () -> service.findById(userId));
	}
	
	@Test
	@DisplayName("On get user returns information about existing user")
	void onGetUserReturnInformationAboutExistingUser() {
		Long userId = 14L;
		
		User user = new User();
		user.setBirthDate(new Date());
		user.setEmail("user.name@user.com");
		user.setId(userId);
		user.setLastname("Name");
		user.setName("User");
		user.setNickname("us");
		
		doReturn(Optional.of(user)).when(repository).findById(userId);
		
		UserDto userDto = service.findById(userId);
		
		assertThat(userDto.getBirthDate()).isEqualTo(user.getBirthDate());
		assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
		assertThat(userDto.getId()).isEqualTo(user.getId());
		assertThat(userDto.getLastname()).isEqualTo(user.getLastname());
		assertThat(userDto.getName()).isEqualTo(user.getName());
		assertThat(userDto.getNickname()).isEqualTo(user.getNickname());
	}
	
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
		
		assertThat(exception.getMessage()).isEqualTo(MessageFormat.format("There is already a user registered for the email {0}.", dto.getEmail()));
	}
	
	@Test
	@DisplayName("Error when creating a user without inform birth date")
	@Disabled("Temporarily disabled until i find a solution to test @valid")
	void errorWhenCreateUserWithoutInformBirthdate() {
		UserCreateDto dto = new UserCreateDto();
		dto.setEmail("user.name@user.com");
		dto.setLastname("Name");
		dto.setName("User");
		
		assertThrows(ConstraintViolationException.class, () -> service.create(dto));
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
	@DisplayName("On create keep nickname when informed and the others fields")
	void onCreateKeepNicknameWhenInfomedAndOthersFields() {
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
		assertThat(user.getBirthDate()).isEqualTo(dto.getBirthDate());
		assertThat(user.getEmail()).isEqualTo(dto.getEmail());
		assertThat(user.getLastname()).isEqualTo(dto.getLastname());
		assertThat(user.getName()).isEqualTo(dto.getName());
	}
	
	@Test
	@DisplayName("Error when update a non-existent user")
	void errorWhenUpdateNonExistentUser() {
		Long userId = 14L;
		
		UserModifyDto dto = new UserModifyDto();
		dto.setBirthDate(new Date());
		dto.setLastname("Name");
		dto.setName("User");
		dto.setNickname("UNme");
		
		doReturn(Optional.empty()).when(repository).findById(userId);
		
		assertThrows(BarberShopNotFoundException.class, () -> service.update(userId, dto));
	}
	
	@Test
	@DisplayName("On update keep the informed fields and don't change others")
	void onUpdateKeepAllInformadFieldsAndDontChangeOthers() throws ParseException {
		Long userId = 14L;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		User user = new User();
		user.setBirthDate(dateFormat.parse("01/01/2000"));
		user.setEmail("user.name@email.com");
		user.setId(userId);
		user.setLastname("Name");
		user.setName("User");
		user.setNickname("Us");
		
		doReturn(Optional.of(user)).when(repository).findById(userId);
		
		UserModifyDto dto = new UserModifyDto();
		dto.setBirthDate(dateFormat.parse("15/06/2001"));
		dto.setLastname("Sedrick");
		dto.setName("Jhon");
		dto.setNickname("Jhon");
		
		service.update(userId, dto);
		
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		
		verify(repository).save(userCaptor.capture());
		
		User alteredUser = userCaptor.getValue();
		
		assertThat(alteredUser.getBirthDate()).isEqualTo(dto.getBirthDate());
		assertThat(alteredUser.getEmail()).isEqualTo(user.getEmail());
		assertThat(alteredUser.getLastname()).isEqualTo(dto.getLastname());
		assertThat(alteredUser.getName()).isEqualTo(dto.getName());
		assertThat(alteredUser.getNickname()).isEqualTo(dto.getNickname());
		assertThat(alteredUser.getId()).isEqualTo(user.getId());
	}
	
	@Test
	@DisplayName("Error when delete a non-existent user")
	void errorWhenDeleteNonExistentUser() {
		Long userId = 14L;
		
		doReturn(Optional.empty()).when(repository).findById(userId);
		
		assertThrows(BarberShopNotFoundException.class, () -> service.delete(userId));
	}
	
	@Test
	@DisplayName("On delete remove a existent user")
	void onDeleteRemoveExistentUser() {
		Long userId = 14L;
		
		User user = new User();
		user.setBirthDate(new Date());
		user.setEmail("user.name@email.com");
		user.setId(userId);
		user.setLastname("Name");
		user.setName("User");
		user.setNickname("Us");
		
		doReturn(Optional.of(user)).when(repository).findById(userId);
		
		service.delete(userId);
		
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		
		verify(repository).delete(userCaptor.capture());
		
		assertThat(userCaptor.getValue()).isEqualTo(user);
	}
	
}
