package com.ssafy.apartment.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;

public interface HousedealService {

	List<HouseDealDto> list() throws Exception;
    List<HouseDealDto> listAll(Map<String, String> map) throws Exception;
    List<HouseInfoDto> listByKeyword(String keyword) throws Exception;
}
