package com.ssafy.board.model;

import lombok.Data;

@Data
public class CommentDto {
	private int commentNo;
	private int articleNo;
	private String userId;
	private String comment;
	
	public CommentDto(int commentNo, int articleNo, String userId, String comment) {
		super();
		this.commentNo = commentNo;
		this.articleNo = articleNo;
		this.userId = userId;
		this.comment = comment;
	}
}
