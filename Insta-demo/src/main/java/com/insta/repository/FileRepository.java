package com.insta.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.insta.modal.LoadFile;

public interface FileRepository extends MongoRepository<LoadFile, Integer> {

	Optional<LoadFile> findByFilename(String fileame);

}
