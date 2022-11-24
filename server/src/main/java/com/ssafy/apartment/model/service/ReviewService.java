package com.ssafy.apartment.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.IntersaleDto;
import com.ssafy.apartment.model.ReviewDto;

public interface ReviewService {

	List<ReviewDto> addReview(ReviewDto reviewDto) throws Exception;
	List<ReviewDto> getReviewList(String aptCode) throws Exception;
	List<ReviewDto> getMyReviewList(String userId) throws Exception;
	ReviewDto getOneReview(ReviewDto reviewDto) throws Exception;
	List<ReviewDto> deleteReview(String reviewNo, String userId) throws Exception;
}
