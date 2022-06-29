package com.barbershop.error;

import java.text.MessageFormat;

public class BarberShopNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BarberShopNotFoundException(String message, Object... messageParams) {
		super(MessageFormat.format(message, messageParams));
	}
	
}
