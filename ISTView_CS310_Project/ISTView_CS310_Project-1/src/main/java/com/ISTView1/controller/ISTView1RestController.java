package com.ISTView1.controller;

import java.util.List;

//import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ISTView1.model.Comments;
import com.ISTView1.model.Locations;
import com.ISTView1.repo.CommentsRepository;
import com.ISTView1.repo.LocationsRepository;
import com.ISTView1.model.CommentsPayload;

@RestController
@RequestMapping("/tourism")
public class ISTView1RestController {
	
	@Autowired private LocationsRepository locationRepository;
	@Autowired private CommentsRepository commentRepository;
	
	//private static final Logger logger = LoggerFactory.getLogger(ISTView1RestController.class);
	
	
	@GetMapping("/locations")
	public List<Locations> locations(){
		
		return locationRepository.findAll();
	
	}
	
	
	@PostMapping("/locations/searchbycategory")
	public List <Locations> locationsInCategory (@RequestBody Locations location){
		
		List <Locations> results = locationRepository.findByCategoryIgnoreCase(location.getCategory());
		return results;
	}
	
	
	@PostMapping("/locations/search")
	public List <Locations> locationsByNames (@RequestBody Locations location){
		
		List <Locations> results = locationRepository.findByNameContainsIgnoreCase(location.getName());
		return results;
	}
	
	
	@PostMapping("/locations/save")
	public Locations saveLocation(@RequestBody Locations location) {
		
		Locations locationSaved = locationRepository.save(location);
		return locationSaved;

	}
	
	
	/*@GetMapping("/comments")
	public List<Comments> comments(){
		return commentRepository.findAll();
	}*/
	
	
	@PostMapping("/comments/reviews")
	public List<Comments> locationReviews(@RequestBody Locations location){
		List<Comments> results= commentRepository.findByLocationsId(location.getId());
		return results;
	}
	
	
	@PostMapping("/comments/addReview")
	public Comments addReview(@RequestBody CommentsPayload payload) {
		
		Locations loc = new Locations();
		loc.setId(payload.getLocationId());
		
		Comments newReview = new Comments(payload.getUserName(), payload.getComment(), payload.getRating(), loc);
		
		Comments resultReview = commentRepository.save(newReview);
		
		return resultReview;
	}
    
		
}
