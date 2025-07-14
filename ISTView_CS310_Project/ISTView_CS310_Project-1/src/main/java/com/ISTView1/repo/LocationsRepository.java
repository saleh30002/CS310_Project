package com.ISTView1.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ISTView1.model.Locations;

public interface LocationsRepository extends MongoRepository<Locations, String>{
	
	public List <Locations> findByNameContainsIgnoreCase(String name);
	public List <Locations> findByCategoryIgnoreCase(String category);

}
