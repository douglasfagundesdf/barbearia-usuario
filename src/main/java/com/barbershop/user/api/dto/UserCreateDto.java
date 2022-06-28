package com.barbershop.user.api.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
	
	@NotEmpty
	@Size(min = 1, max = 80)
	private String name;
	
	@NotEmpty
	@Size(min = 1, max = 80)
	private String lastname;
	
	@Size(min = 1, max = 80)
	private String nickname;
	
	@NotNull
	private Date birthDate;
	
	@NotEmpty
	@Email
	private String email;
	
}
