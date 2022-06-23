package br.com.barbearia.usuario.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity (name = "users")
public class User extends BaseEntity {
	
	private String name;
	
	private String lastname;
	
	private String nickname;
	
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	private String email;
	
}
