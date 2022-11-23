package com.ssafy.apartment.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;

@Mapper
public interface HousedealMapper {
	List<HouseDealDto> list() throws SQLException;
    List<HouseInfoDto> listByKeyword(String keyword) throws SQLException;
    List<HouseDealDto> getHouseDeal(String houseNo) throws SQLException;
    List<HouseInfoDto> getHouseListByDong(String gugun) throws SQLException;
}
