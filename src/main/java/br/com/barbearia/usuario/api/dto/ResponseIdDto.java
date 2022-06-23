package br.com.barbearia.usuario.api.dto;

import lombok.Getter;

@Getter
public class ResponseIdDto<T> {
	
	private Long id;
	
	public ResponseIdDto() {
		
	}

	public ResponseIdDto(Long id) {
		this.id = id;
	}
	
}
