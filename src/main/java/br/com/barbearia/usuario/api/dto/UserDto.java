package br.com.barbearia.usuario.api.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseDto {
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String lastname;
	
	private String nickName;
	
	private Date birthDate;
	
	private String email;
	
}
