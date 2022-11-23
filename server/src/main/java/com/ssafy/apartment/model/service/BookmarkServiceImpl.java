package com.ssafy.apartment.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.HouseInfoDto;
import com.ssafy.apartment.model.IntersaleDto;
import com.ssafy.apartment.model.mapper.BookmarkMapper;
import com.ssafy.apartment.model.mapper.HousedealMapper;

@Service
public class BookmarkServiceImpl implements BookmarkService {
	
	private BookmarkMapper bookmarkMapper;
	private HousedealMapper housedealMapper;
	@Autowired
	public BookmarkServiceImpl(BookmarkMapper bookmarkMapper, HousedealMapper housedealMapper) {
		this.bookmarkMapper = bookmarkMapper;
		this.housedealMapper = housedealMapper;
	}
	
	
	@Override
	public List<IntersaleDto> addBookmark(String aptCode, String userId) throws Exception {
		bookmarkMapper.addBookmark(aptCode, userId);
		return bookmarkMapper.bookmarkList(userId);
	}


	@Override
	public List<IntersaleDto> getBookmarkList(String userId) throws Exception {
		return bookmarkMapper.bookmarkList(userId);
	}


	@Override
	public List<IntersaleDto> deleteBookmark(Map<String,String> map) throws Exception {
		bookmarkMapper.deleteBookmark(map);
		return bookmarkMapper.bookmarkList(map.get("userId"));
	}


	@Override
	public List<HouseInfoDto> getBookmarkInfoList(String[] aptCodes) throws Exception {
		List<HouseInfoDto> houseInfoList = new ArrayList<HouseInfoDto>();
		for(String aptCode: aptCodes) {
			houseInfoList.add(bookmarkMapper.getHouseInfo(aptCode));
		}
		return houseInfoList;
	}

}
