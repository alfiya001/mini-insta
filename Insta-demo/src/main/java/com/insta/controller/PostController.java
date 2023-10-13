package com.insta.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.insta.modal.LoadFile;
//import com.insta.repository.PostRepository;
import com.insta.modal.Post;
import com.insta.repository.PostRepository;
import com.insta.service.PostService;
import com.insta.serviceImpl.SequenceGeneratorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

	private final PostRepository postRepository;
	
	private final PostService postService;
	
	private final SequenceGeneratorService service;
	
	@PostMapping("/post")
    public ResponseEntity < Post > createProduct(@RequestBody Post post){
		post.setId(service.getSequenceNumber(post.SEQUENCE_NAME));
		post.setCreatedAt(LocalDateTime.now());
		post.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok().body(postService.addPost(post));
    }


	@GetMapping("/post")
    public ResponseEntity<?> getPost() {
		
		List<Post> posts = postService.getAllPost();
		if(posts.isEmpty())
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().body(posts);
    }
	
	@PutMapping("/post/{id}")
	public ResponseEntity<?> updatePost(@RequestBody Post post, @PathVariable Integer id) {
		
		Post postD = postService.getPostById(id).get();
		if(postService.getPostById(id).isPresent()) {
			postD.setCaption(post.getCaption());
			postD.setCommentCount(post.getCommentCount());
			postD.setLikecunt(post.getLikecunt());
			postD.setUpdatedAt(LocalDateTime.now());
			return ResponseEntity.ok().body(postService.addPost(postD));
		}
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/post/{id}")
	public ResponseEntity<?> getPostbyId(@PathVariable Integer id) {
		Optional<Post> post = postService.getPostById(id);
		if(post.isPresent())
			return ResponseEntity.ok().body(postService.getPostById(id));
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/post/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Integer id) {
		Optional<Post> post = postService.getPostById(id);
		if(!post.isPresent())
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
		
		postService.deletePost(post.get());
		return ResponseEntity.ok().body(HttpStatus.NO_CONTENT);
	}
	
//	@PostMapping("/upload")
//    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file) throws IOException {
//        return new ResponseEntity<>(postService.addFile(file), HttpStatus.OK);
//    }
//
//    @GetMapping("/download/{id}")
//    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
//        LoadFile loadFile = postService.downloadFile(id);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(loadFile.getFileType() ))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
//                .body(new ByteArrayResource(loadFile.getFile()));
//    }
    
    @ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/upload")
	public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile file) throws IOException{
    	return ResponseEntity.status(HttpStatus.OK).body(postService.uploadImage(file));
	}
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
		byte[] image = postService.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	}
}
