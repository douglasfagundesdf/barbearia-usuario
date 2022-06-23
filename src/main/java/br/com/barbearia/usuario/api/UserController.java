package br.com.barbearia.usuario.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.barbearia.usuario.api.dto.UserIncomingDto;

@RestController
@RequestMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@PostMapping
	public ResponseEntity<Long> create(@RequestBody @Valid UserIncomingDto userIncomingDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(1L);
	}
	
}
