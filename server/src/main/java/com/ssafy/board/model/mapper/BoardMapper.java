package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> listArticle(Map<String, Object> map) throws SQLException;
	BoardDto getArticle(int articleNo) throws SQLException;
	void writeArticle(BoardDto board) throws SQLException;
	void modifyArticle(BoardDto board) throws SQLException;
	void deleteArticle(int articleNo) throws SQLException;
	int totalArticleCount(Map<String, String> map) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	// for pagination (page-link)
	List<BoardDto> selectBoardLimitOffset(int limit, int offset, String key, String word) throws SQLException;
	int selectBoardTotalCount() throws SQLException;
}
