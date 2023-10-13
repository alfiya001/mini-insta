package com.insta.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "userDetails")
public class UserDetails {
	
	@Id
	private long userId;
	
	@NotBlank
	@Size(max = 30)
	private String firstName;
	
	
	@Size(max = 30)
	private String lastName;
	
	private Gender gender;
	
	@Size(max = 1000)
	private String bio;
	
	private String profilePhoto;
	
	@NotBlank
	private Privacy privacy;
	
	private LocalDate dob;
	
	@NotBlank
	private LocalDate doj;
	
	private LocalDateTime lastLogin;
	
	@Size(max = 10)
	private long mobile;
}
