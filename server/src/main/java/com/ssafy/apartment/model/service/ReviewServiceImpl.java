package com.ssafy.apartment.model.service;

import java.util.List;
import java.util.Map;

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

	@Override
	public ReviewDto getOneReview(ReviewDto reviewDto) throws Exception {
		return reviewMapper.getOneReview(reviewDto);
	}

	@Override
	public List<ReviewDto> getMyReviewList(String userId) throws Exception {
		return reviewMapper.getMyReviewList(userId);
	}

	@Override
	public List<ReviewDto> deleteReview(String reviewNo, String userId) throws Exception {
		reviewMapper.deleteReview(reviewNo);
		return reviewMapper.getMyReviewList(userId);
	}

}
