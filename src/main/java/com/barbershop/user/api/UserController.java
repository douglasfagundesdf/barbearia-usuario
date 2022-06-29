package com.barbershop.user.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbershop.api.ResponseIdDto;
import com.barbershop.user.domain.dto.UserCreateDto;
import com.barbershop.user.domain.dto.UserDto;
import com.barbershop.user.domain.dto.UserModifyDto;
import com.barbershop.user.domain.service.UserService;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
		UserDto user = service.findById(id);
		
		return ResponseEntity.ok(user);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseIdDto<Long>> create(@Valid @RequestBody UserCreateDto userIncomingDto) {
		Long id = service.create(userIncomingDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseIdDto<>(id));
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody UserModifyDto userModifyDto) {
		if (service.update(id, userModifyDto)) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (service.delete(id)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
