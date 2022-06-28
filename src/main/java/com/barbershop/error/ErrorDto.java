package com.barbershop.error;

import lombok.Getter;

@Getter
public class ErrorDto {
	
	private String errorMessage;

	public ErrorDto(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
