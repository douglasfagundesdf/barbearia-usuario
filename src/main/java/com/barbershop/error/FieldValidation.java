package com.barbershop.error;

import lombok.Getter;

@Getter
public class FieldValidation {
	
	private final String field;
	
	private final String message;
	
	public FieldValidation(String field, String message) {
		this.field = field;
		this.message = message;
	}
	
}
