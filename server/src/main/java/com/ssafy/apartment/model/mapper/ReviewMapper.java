package com.ssafy.apartment.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.IntersaleDto;
import com.ssafy.apartment.model.ReviewDto;

@Mapper
public interface ReviewMapper {
	
	void addReview(ReviewDto reviewDto) throws SQLException;
	void deleteReview(String reviewNo) throws SQLException;
	List<ReviewDto> getReviewList(String aptCode) throws SQLException;
	List<ReviewDto> getMyReviewList(String userId) throws SQLException;
	ReviewDto getOneReview(ReviewDto reviewDto) throws SQLException;
}
