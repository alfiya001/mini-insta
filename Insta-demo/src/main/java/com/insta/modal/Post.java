package com.insta.modal;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Document(collation = "post")
public class Post {
	
	@Transient
	public static final String SEQUENCE_NAME = "user_sequence";
	
	@Id
	private int id;
	
	private long likecunt;
	
	private long commentCount;
	
    @Size(max = 100)
	private String caption;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private String image;
    
}
