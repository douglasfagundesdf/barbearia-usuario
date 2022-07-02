package com.barbershop.user.domain.dto;

import java.util.Date;

import com.barbershop.api.BaseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends BaseDto {
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String lastname;
	
	private String nickname;
	
	private Date birthDate;
	
	private String email;
	
}
