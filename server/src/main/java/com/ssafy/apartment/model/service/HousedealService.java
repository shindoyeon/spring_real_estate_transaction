package com.ssafy.apartment.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;

public interface HousedealService {

	List<HouseDealDto> list() throws Exception;
    List<HouseInfoDto> listByKeyword(String keyword) throws Exception;
	List<HouseDealDto> getHouseDeal(String houseNo) throws Exception;
	List<HouseInfoDto> getHouseListByDong(String gugun) throws Exception;
}
