package com.ssafy.apartment.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<SidoGugunCodeDto> getDongInGugun(String gugun) throws SQLException;
}
