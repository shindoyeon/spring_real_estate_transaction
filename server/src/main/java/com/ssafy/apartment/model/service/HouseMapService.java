package com.ssafy.apartment.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<SidoGugunCodeDto> getDongInGugun(String gugun) throws Exception;
	
}
