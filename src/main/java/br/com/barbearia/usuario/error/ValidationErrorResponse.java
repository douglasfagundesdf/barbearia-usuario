package br.com.barbearia.usuario.error;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {
	
	private List<FieldValidation> errors = new ArrayList<>();
	
	public void addError(FieldValidation fieldError) {
		errors.add(fieldError);
	}
	
	public List<FieldValidation> getErrors() {
		return errors;
	}
	
}
