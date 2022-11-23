package com.ssafy.member.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.member.model.MemberDto;

public interface MemberService {

	int idCheck(String userId) throws Exception; // 아이디 중복검사
	void joinMember(MemberDto memberDto) throws Exception; // 회원가입
	MemberDto loginMember(MemberDto memberDto) throws Exception; // 로그인
	boolean findPassword(MemberDto memberDto) throws Exception; 
	MemberDto getMember(String userId) throws Exception;
	void deleteMember(String userId) throws Exception;
	MemberDto updateMember(MemberDto memberDto) throws Exception;
	List<MemberDto> listMember() throws Exception;	
	void saveRefreshToken(String userid, String refreshToken) throws Exception;
	Object getRefreshToken(String userid) throws Exception;
	void deleRefreshToken(String userid) throws Exception;
	int updateDelflag(MemberDto memberDto) throws Exception;
}
