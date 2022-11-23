package com.ssafy.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	private MemberMapper memberMapper;
	
	@Autowired
	private MemberServiceImpl(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}

	@Override
	public void joinMember(MemberDto memberDto) throws Exception {
		memberMapper.joinMember(memberDto);
	}

	@Override
	public MemberDto loginMember(MemberDto memberDto) throws Exception {
		return memberMapper.loginMember(memberDto);
	}
	
	@Override
	public boolean findPassword(MemberDto memberDto) throws Exception {
		MemberDto findmemberDto = memberMapper.findPassword(memberDto.getUserId());
		
		//입력한 이메일과 저장된 이메일이 같은 경우 임시 비밀번호 발급 
		if (findmemberDto != null && findmemberDto.getUserEmail().equals(memberDto.getUserEmail())) {
			// 임시 비밀번호 발급
			final String newPassword = getRandomPassword(12);
			findmemberDto.setUserPassword(newPassword);
			// 새로운 비밀번호 업데이트
			if (memberMapper.updatePassword(findmemberDto) == 1 && sendInitPwEmail(findmemberDto)) {
				// 성공
				return true;
			} else {
				// 실패
				return false;
			}

		} else {
			return false;
		}
	}
	
	@Override
	public MemberDto getMember(String userId) throws Exception {
		return memberMapper.getMember(userId);
	}

	@Override
	public void deleteMember(String userId) throws Exception {
		memberMapper.deleteMember(userId);
	}

	@Override
	public MemberDto updateMember(MemberDto memberDto) throws Exception {
		memberMapper.updateMember(memberDto);
		return memberMapper.getMember(memberDto.getUserId());
	}

	@Override
	public List<MemberDto> listMember() throws Exception {
		return memberMapper.listMember();
	}

	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userid);
		map.put("token", refreshToken);
		System.out.println("memberserviceimpl: "+map);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return memberMapper.getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);

	}

	@Override
	public int updateDelflag(MemberDto memberDto) throws Exception {
		return memberMapper.updateDelflag(memberDto);
	}

	private static String getRandomPassword(int len) {

        char[] charSet = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '@', '#', '$', '%', '^', '&', '!', '*'
        };
        StringBuilder sb = new StringBuilder(len);
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(charSet[random.nextInt(charSet.length)]);
        }
        return sb.toString();
    }

	private static boolean sendInitPwEmail(MemberDto memberDto) {

		final String charSet = "utf-8";
        final String hostSMTP = "smtp.naver.com";
        final String hostSMTPid = "dyeon7310";
        final String hostSMTPpwd = "monica8986*";

        final String fromEmail = "dyeon7310@naver.com";
        final String fromName = "HAPPY HOUSE";
        final String subject = "Happy House 계정 패스워드 초기화 정보입니다.";
        String msg = "<div style='border: 1px solid black; padding: 10px; font-family: verdana;'>";
        msg += "<h2>안녕하세요. <span style='color: blue;'>" + memberDto.getUserName() + "</span>님.</h2>";
        msg += "<p>초기화된 비밀번호를 전송해 드립니다. 비밀번호를 변경하여 사용하세요.</p>";
        msg += "<p>임시 비밀번호 : <span style='color: blue;'>" + memberDto.getUserPassword() + "</span></p></div>";

        try {
            HtmlEmail email = new HtmlEmail();
            email.setDebug(true);
            email.setCharset(charSet);
            email.setSSLOnConnect(true);
            email.setHostName(hostSMTP);
            email.setSmtpPort(587);

            email.setAuthentication(hostSMTPid, hostSMTPpwd);
            email.setStartTLSEnabled(true);
            email.addTo(memberDto.getUserEmail(), memberDto.getUserName(), charSet);
            email.setFrom(fromEmail, fromName, charSet);
            email.setSubject(subject);
            email.setHtmlMsg(msg);
            email.send();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
