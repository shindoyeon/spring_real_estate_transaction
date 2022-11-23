package com.ssafy.apartment.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.IntersaleDto;

public interface BookmarkService {

	List<IntersaleDto> addBookmark(String aptCode,String userId) throws Exception;
	List<IntersaleDto> deleteBookmark(Map<String,String> map) throws Exception;
	List<IntersaleDto> getBookmarkList(String userId) throws Exception;
	List<HouseInfoDto> getBookmarkInfoList(String[] aptCodes) throws Exception;
}
