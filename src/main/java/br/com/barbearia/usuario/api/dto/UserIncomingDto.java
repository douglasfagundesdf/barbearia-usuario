package br.com.barbearia.usuario.api.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIncomingDto {
	
	@NotNull
	private String name;
	
	@NotNull
	private String lastname;
	
	private String nickName;
	
	@NotNull
	private Integer age;
	
}
