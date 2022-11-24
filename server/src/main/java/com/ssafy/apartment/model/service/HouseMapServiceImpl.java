package com.ssafy.apartment.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.SidoGugunCodeDto;
import com.ssafy.apartment.model.mapper.HouseMapMapper;
import com.ssafy.apartment.model.mapper.HousedealMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
private HouseMapMapper houseMapMapper;
	
	@Autowired
	public HouseMapServiceImpl(HouseMapMapper houseMapMapper) {
		this.houseMapMapper = houseMapMapper;
	}

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<SidoGugunCodeDto> getDongInGugun(String gugun) throws Exception {
		return houseMapMapper.getDongInGugun(gugun);
	}	
}
