package com.insta.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.insta.modal.Post;

public interface PostRepository extends MongoRepository<Post, Integer> {

}
