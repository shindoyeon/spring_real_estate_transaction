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
import com.ssafy.apartment.model.service.BookmarkService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bookmark")
@Api("Bookmark 컨트롤러  API V1")
@CrossOrigin("*")
public class BookmarkController {

	private final Logger logger = LoggerFactory.getLogger(BookmarkController.class);

	@Autowired
	private BookmarkService bookmarkService;

	@ApiOperation(value = "북마크 추가", notes = "북마크를 추가하고 리스트를 반환한다.", response = List.class)
	@PutMapping("/{aptCode}/{userId}")
	public ResponseEntity<List<IntersaleDto>> addBookmark(@PathVariable("aptCode") String aptCode, @PathVariable("userId") String userId) throws Exception {
		logger.info(" addBookmark - 호출");
		return new ResponseEntity<List<IntersaleDto>>(bookmarkService.addBookmark(aptCode, userId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "북마크 삭제", notes = "북마크를 삭제하고 리스트를 반환한다.", response = List.class)
	@DeleteMapping("/{aptCode}/{userId}")
	public ResponseEntity<List<IntersaleDto>> deleteBookmark(@PathVariable("aptCode") String aptCode, @PathVariable("userId") String userId) throws Exception {
		logger.info(" deleteBookmark - 호출");
		Map<String, String> map = new HashMap();
		map.put("aptCode",aptCode);
		map.put("userId",userId);
		return new ResponseEntity<List<IntersaleDto>>(bookmarkService.deleteBookmark(map), HttpStatus.OK);
	}
	
	@ApiOperation(value = "북마크 리스트", notes = "북마크 리스트를 반환한다.", response = List.class)
	@GetMapping("/{userId}")
	public ResponseEntity<List<IntersaleDto>> getBookmarkList( @PathVariable("userId") String userId) throws Exception {
		logger.info(" getBookmarkList - 호출");
		return new ResponseEntity<List<IntersaleDto>>(bookmarkService.getBookmarkList(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "북마크 정보 리스트", notes = "북마크 정보 리스트를 반환한다.", response = List.class)
	@GetMapping("/info/{aptCodes}")
	public ResponseEntity<?> getBookmarkInfoList(@PathVariable("aptCodes") String[] aptCodes) throws Exception {
		logger.info(" getBookmarkInfoList - 호출 aptCode 개수:{}", aptCodes.length);
		return new ResponseEntity<List<HouseInfoDto>>(bookmarkService.getBookmarkInfoList(aptCodes), HttpStatus.OK);
	}

}
