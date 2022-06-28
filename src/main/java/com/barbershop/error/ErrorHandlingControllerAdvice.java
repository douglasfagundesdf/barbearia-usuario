package com.barbershop.error;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
	
	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
		ValidationErrorResponse error = new ValidationErrorResponse();
		
		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			error.addError(new FieldValidation(violation.getPropertyPath().toString(), violation.getMessage()));
		}
		
		return error;
	}
	
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ValidationErrorResponse error = new ValidationErrorResponse();
		
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.addError(new FieldValidation(fieldError.getField(), fieldError.getDefaultMessage()));
		}
		
		return error;
	}
	
	@ResponseBody
	@ExceptionHandler(BarberShopException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDto onDomainLogicError(BarberShopException e) {
		return new ErrorDto(e.getMessage());
	}
	
}
