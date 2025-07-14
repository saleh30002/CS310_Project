package com.ISTView1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comments {
	
	@Id private String id;
	
	private String userName;
	private String comment;
	private double rating;
	@DBRef
	private Locations locations;
	
	public Comments() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Comments(String userName, String comment, double rating, Locations locations) {
		super();
		this.userName = userName;
		this.comment = comment;
		this.rating = rating;
		this.locations = locations;
	}


	public Comments(String id, String userName, String comment, double rating, Locations locations) {
		super();
		this.id = id;
		this.userName = userName;
		this.comment = comment;
		this.rating = rating;
		this.locations = locations;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public Locations getLocations() {
		return locations;
	}


	public void setLocations(Locations locations) {
		this.locations = locations;
	}


	@Override
	public String toString() {
		return "Comments [id=" + id + ", userName=" + userName + ", comment=" + comment + ", rating=" + rating
				+ ", locations=" + locations + "]";
	}

}
