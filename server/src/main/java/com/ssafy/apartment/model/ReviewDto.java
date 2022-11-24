package com.ssafy.apartment.model;

import lombok.Data;

@Data
public class ReviewDto {
	private String reviewNo;
	private String userId;
	private String aptCode;
	private String rating;
	private String content;
	private String registerTime;
	
	private String apartmentName;
}
