package com.ISTView1.model;

public class CommentsPayload {
	
	private String userName;
	private String comment;
	private double rating;
	private String locationId;
	
	
	public CommentsPayload() {
		
	}


	public CommentsPayload(String userName, String comment, double rating, String locationId) {
		super();
		this.userName = userName;
		this.comment = comment;
		this.rating = rating;
		this.locationId = locationId;
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


	public String getLocationId() {
		return locationId;
	}


	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}


	@Override
	public String toString() {
		return "CommentsPayload [userName=" + userName + ", comment=" + comment + ", rating=" + rating + ", locationId="
				+ locationId + "]";
	}
	
}
