package com.ssafy.apartment.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.IntersaleDto;

@Mapper
public interface BookmarkMapper {
	void addBookmark(String aptCode,String userId) throws SQLException;
	void deleteBookmark(Map<String,String> map) throws Exception;
	List<IntersaleDto> bookmarkList(String userId) throws SQLException;
	HouseInfoDto getHouseInfo(String aptCode) throws SQLException;
}
