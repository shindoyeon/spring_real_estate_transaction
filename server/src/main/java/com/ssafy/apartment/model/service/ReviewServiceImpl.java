package com.ssafy.apartment.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.apartment.model.ReviewDto;
import com.ssafy.apartment.model.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	private ReviewMapper reviewMapper;
	
	@Autowired
	public ReviewServiceImpl(ReviewMapper reviewMapper) {
		this.reviewMapper = reviewMapper;
	}
	
	@Override
	public List<ReviewDto> addReview(ReviewDto reviewDto) throws Exception {
		reviewMapper.addReview(reviewDto);
		return reviewMapper.getReviewList(reviewDto.getAptCode());
	}

	@Override
	public List<ReviewDto> getReviewList(String aptCode) throws Exception {
		return reviewMapper.getReviewList(aptCode);
	}

}
