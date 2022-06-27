package com.barbershop.api;

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
