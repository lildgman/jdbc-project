package com.kh.controller;

import java.util.ArrayList;
import java.util.Iterator;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

/*
 * Controller: View를 통해서 사용자가 요청한 기능에 대해서 처리하는 담당
 * 			   해당 메서드로 전달된 데이터[가공처리후] dao로 전달하며 호출
 * 			   dao로부터 반환받은 결과가 성공인지 실패인지 판단 후 응답화면 결정(view 메서드 호출)
 * 
 */
public class MemberController {
	
	
	// 사용자 추가요청을 처리하는 메서드
	// @param userId ~ hobby: 사용자가 입력했던 정보들이 담겨있는 매개변수
	public void insertMember(String userId, String userPwd, String userName, String gender,
			String age, String email, String phone, String address,String hobby) {
		
		// View로부터 전달받은 값을 DAO쪽으로 전달x
		// 어딘가(Member객체(vo))에 담아서 전달
		
		//1) 기본 생성자로 생성 후 각 필드마다 setter를 이용해 데이터 저장
		//2) 매개변수 있는 생성자로 전부 전달해서 생성
		Member member = new Member(userId,userPwd,userName,gender,Integer.parseInt(age),email,phone,address,hobby);
		
		int result = new MemberDao().insertMember(member);
		if(result > 0) { // insert 성공
			// 성광화면
			new MemberMenu().displaySuccess("회원이 성공적으로 추가되었습니다.");
		} else { // insert 실패
			// 실패화면
			new MemberMenu().displayFail("회원 추가를 실패하였습니다.");
		}
	}

	
	/**
	 * 사용자 모두를 조회하는 메서드
	 */
	public void selectMemberList() {
		
		ArrayList<Member> memberList = new MemberDao().selectMemberList();

		
		if(memberList.isEmpty()) {
			new MemberMenu().displayNoData("현재 회원이 없습니다.");
		} else {
			new MemberMenu().displayData(memberList);
		}
		
//		Iterator<Member> it = memberList.iterator();
//		while (it.hasNext()) {
//			System.out.println(it.next());
//		}
	}


	public void updateMemberStatus(String userId, String userPwd, String email, String phone, String address) {
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);
		
		int result = new MemberDao().updateMemberStatus(member);
		
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 변경 하였습니다.");
		} else {
			new MemberMenu().displayFail("변경에 실패하였습니다.");
		}
		
	}


	public void deleteMember(String inputMemberId) {
		int result = new MemberDao().deleteMember(inputMemberId);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원을 삭제하였습니다.");
		} else {
			new MemberMenu().displayFail("회원 삭제를 실패하였습니다.");
		}
		
	}


	public void searchMemberById(String userId) {
		Member member = new MemberDao().searchMemberById(userId);
		
		if (member == null) {
			new MemberMenu().displayFail("해당 회원이 없습니다.");
		} else {
			new MemberMenu().displayMember(member);
		}
	}

}
