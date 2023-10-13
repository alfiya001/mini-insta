package com.insta.serviceImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.insta.modal.LoadFile;
import com.insta.modal.Post;
import com.insta.repository.FileRepository;
import com.insta.repository.PostRepository;
import com.insta.service.PostService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.*;
import org.apache.commons.io.IOUtils;
import java.io.File;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	private final GridFsTemplate template;

	private final GridFsOperations operations;
	
	private final FileRepository fileRepository;
	
	private final SequenceGeneratorService service;

	@Override
	public List<Post> getAllPost() {
		// TODO Auto-generated method stub
		return postRepository.findAll();
	}

	@Override
	public Post addPost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Optional<Post> getPostById(Integer id) {
		// TODO Auto-generated method stub
		return postRepository.findById(id);
	}

	@Override
	public void deletePost(Post post) {
		postRepository.delete(post);
	}

	@Override
	public String addFile(MultipartFile upload) throws IOException {

		DBObject metadata = new BasicDBObject();
		metadata.put("fileSize", upload.getSize());

		Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(),
				metadata);

		return fileID.toString();
	}

	@Override
	public LoadFile downloadFile(String id) throws IOException {

        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );

        LoadFile loadFile = new LoadFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename( gridFSFile.getFilename() );

            loadFile.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );

            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

            loadFile.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
        }

        return loadFile;
    }
	
private final String PATH = "C:\\Users\\2106421\\OneDrive - Cognizant\\Documents\\alfyaPractice\\Insta\\Insta-demo\\src\\main\\resources\\upload\\";
	
@Override
	public LoadFile uploadImage(MultipartFile file) throws IOException {
		String fullPath = PATH+file.getOriginalFilename();
		LoadFile pImage = new LoadFile();
		int id = service.getSequenceNumber(pImage.SEQUENCE_NAME);
		pImage.setId(id);
		pImage.setFilename("_"+id+file.getOriginalFilename());
		pImage.setFileType(file.getContentType());
		pImage.setFilepath(fullPath);
		
		file.transferTo(new File(fullPath));
		fileRepository.save(pImage);
		return pImage;
	}
	
	@Override
	public byte[] downloadImage(String fileName) throws IOException{
        Optional<LoadFile> imageObject = fileRepository.findByFilename(fileName);
        log.info("log:"+imageObject.get().getFilepath());
        
        String fullPath = imageObject.get().getFilepath();
        return Files.readAllBytes(new File(fullPath).toPath());
    }

}
