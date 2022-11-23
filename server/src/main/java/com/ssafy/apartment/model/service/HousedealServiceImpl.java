package com.ssafy.apartment.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.mapper.HouseMapMapper;
import com.ssafy.apartment.model.mapper.HousedealMapper;

@Service
public class HousedealServiceImpl implements HousedealService{
	
	private HousedealMapper housedealMapper;
	
	@Autowired
	public HousedealServiceImpl(HousedealMapper housedealMapper) {
		this.housedealMapper = housedealMapper;
	}
	
	@Override
	public List<HouseDealDto> list() throws Exception {
		System.out.println("HousedealServiceImpl");
		return housedealMapper.list();
	}

	
	@Override
	public List<HouseInfoDto> listByKeyword(String keyword) throws Exception {
		return housedealMapper.listByKeyword(keyword);
	}

	@Override
	public List<HouseDealDto> getHouseDeal(String houseNo) throws Exception {
		return housedealMapper.getHouseDeal(houseNo);
	}
	@Override
	public List<HouseInfoDto> getHouseListByDong(String dong) throws Exception {
		return housedealMapper.getHouseListByDong(dong);
	}
	
}
