package com.ssafy.apartment.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.service.HousedealService;
import com.ssafy.board.model.BoardDto;

@Controller
@RequestMapping(value = "/house")
public class HousedealController {

	private HousedealService housedealService; // 관심지역

	@Autowired
	public HousedealController(HousedealService housedealService) {
		this.housedealService = housedealService;
	}

	private static final Logger logger = LoggerFactory.getLogger(HousedealController.class);

	@GetMapping(value = "/mvaptapi")
	public String mvaptapi(Model model) throws Exception {
		logger.debug("ApartmentController ! mvaptapi  ");
		return "/apartment/aptapi";
	}
	
//	@GetMapping(value = "/search")
//	@ResponseBody
//	public ResponseEntity<?> search(@RequestParam Map<String, String> map, Model model) throws Exception {
//		logger.debug("ApartmentController ! search : {}", map);
//		try {
//			List<HouseDealDto> list = housedealService.listAll(map);
//			logger.debug("ApartmentController ! search : {}",list.size());
//			if (list != null && !list.isEmpty()) {
//				return new ResponseEntity<List<HouseDealDto>>(list, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//			}
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	
	@GetMapping(value= "/list/keyword/{keyword}")
	@ResponseBody
	public ResponseEntity<List<HouseInfoDto>> keywordByList(@PathVariable("keyword") String keyword) throws Exception {
		logger.debug("keywordByList - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(housedealService.listByKeyword(keyword), HttpStatus.OK);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value= "/dong/apts/{dong}")
	@ResponseBody
	public ResponseEntity<List<HouseInfoDto>> aptListBydong(@PathVariable("dong") String dong) throws Exception {
		logger.debug("aptListBydong - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(housedealService.listByKeyword(dong), HttpStatus.OK);
	}
	
	// 매물 실거래가 조회
	@GetMapping("/dealList/{houseNo}")
	public ResponseEntity<?> getHouseDeal(@PathVariable("houseNo") String houseNo) {
		logger.debug("ApartmentController ! getHouseDeal : {}", houseNo);
		List<HouseDealDto> list;
		
		try {
			list = housedealService.getHouseDeal(houseNo);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/list/{dong}")
	public ResponseEntity<List<HouseInfoDto>> houseListByDong(@PathVariable("dong") String dong) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(housedealService.getHouseListByDong(dong), HttpStatus.OK);
	}
}
