package com.insta.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
//@Document(collation = "UserFollower")
public class UserFollower {
	
	@Id
	private long id;
	
	private UserDetails userId;
	
	private UserDetails followerId;
	
	private LocalDate dateAdded;
}
