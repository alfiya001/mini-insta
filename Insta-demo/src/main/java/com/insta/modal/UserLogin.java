package com.insta.modal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Document(collation = "userLogin")
public class UserLogin {

	@Id
	private long id;
	
	@NotBlank
	@Size(max = 20)
	@Indexed(unique = true)
	private String username;
	
	@NotBlank
	@Indexed(unique = true)
	private String email;
	
	@NotBlank
	@Indexed(unique = true)
	@Size(max = 16)
	private String password;
	
	private UserDetails userDetails;
}
