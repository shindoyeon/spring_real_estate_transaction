package com.ssafy.member.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	private String userId;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userPhone;
	private String userRole;
	private String delflag;
	private String token;
	
}
