package com.ssafy.apartment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.IntersaleDto;
import com.ssafy.apartment.model.ReviewDto;
import com.ssafy.apartment.model.service.BookmarkService;
import com.ssafy.apartment.model.service.ReviewService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/review")
@Api("Review 컨트롤러  API V1")
@CrossOrigin("*")
public class ReviewController {

	private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	@Autowired
	private ReviewService reviewService;

	@ApiOperation(value = "리뷰 추가", notes = "리뷰 추가하고 리스트를 반환한다.", response = List.class)
	@PutMapping("/")
	public ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto) throws Exception {
		logger.info(" addReview {} - 호출",reviewDto);
		return new ResponseEntity<List<ReviewDto>>(reviewService.addReview(reviewDto), HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "그 아파트 리뷰 리스트", notes = "그 아파트의 리뷰 리스트를 반환한다.", response = List.class)
	@GetMapping("/{aptCode}")
	public ResponseEntity<?> listReview(@PathVariable(value="aptCode") String aptCode) throws Exception {
		logger.info(" listReview {} - 호출", aptCode);
		return new ResponseEntity<List<ReviewDto>>(reviewService.getReviewList(aptCode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "내 리뷰 리스트", notes = "나의 리뷰 리스트를 반환한다.", response = List.class)
	@GetMapping("/my/{userId}")
	public ResponseEntity<?> listMyReview(@PathVariable(value="userId") String userId) throws Exception {
		logger.info(" listMyReview {} - 호출", userId);
		return new ResponseEntity<List<ReviewDto>>(reviewService.getMyReviewList(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "리뷰 하나 가져오기", notes = "입력한 리뷰 하나를 반환한다.", response = List.class)
	@GetMapping("/one")
	public ResponseEntity<?> oneReview(@RequestBody ReviewDto reviewDto) throws Exception {
		logger.info(" oneReview {} - 호출", reviewDto);
		return new ResponseEntity<ReviewDto>(reviewService.getOneReview(reviewDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "리뷰 하나 삭제하기", notes = "입력한 리뷰 하나를 반환한다.", response = List.class)
	@DeleteMapping("/{reviewNo}/{userId}")
	public ResponseEntity<?> deleteReview(@PathVariable(value="reviewNo") String reviewNo,@PathVariable(value="userId") String userId) throws Exception {
		logger.info(" deleteReview {} - {} - 호출", reviewNo,userId);
		return new ResponseEntity<List<ReviewDto>>(reviewService.deleteReview(reviewNo,userId), HttpStatus.OK);
	}

}
