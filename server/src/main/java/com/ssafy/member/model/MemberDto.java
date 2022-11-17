package com.ssafy.member.model;

import com.ssafy.apartment.model.HouseDealDto;

import lombok.Data;

@Data
public class MemberDto {
	private String userId;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userPhone;
	private String userRole;
	private String delflag;
	
	public MemberDto(String userId, String userName, String userPassword, String userEmail, String userPhone,
			String userRole, String delflag) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userRole = userRole;
		this.delflag = delflag;
	}	
}
