package com.insta.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.insta.modal.Post;
import com.insta.modal.LoadFile;

public interface PostService {

	List<Post> getAllPost();

	Post addPost(Post post);

	Optional<Post> getPostById(Integer id);

	void deletePost(Post post);
	
	String addFile(MultipartFile upload) throws IOException;

	LoadFile downloadFile(String id) throws IOException;

	LoadFile uploadImage(MultipartFile file) throws IOException;

	byte[] downloadImage(String fileName) throws IOException;

}
