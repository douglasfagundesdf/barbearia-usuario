package com.barbershop.error;

import java.text.MessageFormat;

public class BarberShopException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BarberShopException(String message, Object... messageParams) {
		super(MessageFormat.format(message, messageParams));
	}
	
	public BarberShopException(Throwable cause, String message, Object... messageParams) {
		super(MessageFormat.format(message, messageParams), cause);
	}
	
}
