package com.ssafy.board.model;

import lombok.Data;

@Data
public class BoardDto {
	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private int hit;
	private int likes;
	private String registerTime;

	public BoardDto(String userId, String subject, String content) {
		super();
		this.userId = userId;
		this.subject = subject;
		this.content = content;
	}
}
