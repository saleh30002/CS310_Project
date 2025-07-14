package com.ISTView1.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ISTView1.model.Comments;
import com.ISTView1.model.Locations;

public interface CommentsRepository extends MongoRepository<Comments, String> {
	
	public List<Comments> findByLocationsId (String id);
}