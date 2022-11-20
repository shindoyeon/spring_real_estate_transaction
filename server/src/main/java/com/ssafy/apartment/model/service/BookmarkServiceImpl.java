package com.ssafy.apartment.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.apartment.model.IntersaleDto;
import com.ssafy.apartment.model.mapper.BookmarkMapper;

@Service
public class BookmarkServiceImpl implements BookmarkService {
	
	private BookmarkMapper bookmarkMapper;
	
	@Autowired
	public BookmarkServiceImpl(BookmarkMapper bookmarkMapper) {
		this.bookmarkMapper = bookmarkMapper;
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
	public List<IntersaleDto> deleteBookmark(String aptCode, String userId) throws Exception {
		bookmarkMapper.deleteBookmark(aptCode, userId);
		return bookmarkMapper.bookmarkList(userId);
	}

}
