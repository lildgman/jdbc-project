package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

/*
 * View: 사용자가 보게될 시작적인 요소(화면) 출력 및 입력
 * 
 * 
 */
public class MemberMenu {

	// Scanner 객체 생성(전역으로 다 입력받을 수 있도록)
	private Scanner sc = new Scanner(System.in);

	// MemberController 객체생성(전역에서 바로 요청할 수 있도록)
	private MemberController mc = new MemberController();

	/*
	 * 사용자가 보게 될 첫 화면(메인화면)
	 */

	public void mainMenu() {

		while (true) {
			System.out.println("=============회원관리 프로그램============");
			System.out.println("1. 회원 추가");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디 검색");
//			System.out.println("4. 회원 이름으로 키워드 검색");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("0. 프로그램 종료");

			System.out.print("메뉴 입력: ");
			int menu = sc.nextInt();
			sc.nextLine();

			switch (menu) {
			case 1:
				inputMember();
				break;
			case 2:
				mc.selectMemberList();
				break;
			case 3:
				searchMemberById();
				break;
//			case 4:
			case 5:
				updateMemberStatus();
				break;
			case 6:
				deleteMember();
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				return;
			default:
				System.out.println("번호를 잘못 입력하셨습니다. 다시 입력해주세요.");

			}
		}
	}
	
	private void searchMemberById() {
		System.out.print("찾을 아이디: ");
		String userId = sc.nextLine();
		mc.searchMemberById(userId);
		
	}

	// 1. 회원 추가
	private void inputMember() {
		System.out.println("=======회원추가=======");
		
		System.out.print("아이디: ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호: ");
		String userPwd = sc.nextLine();
		
		System.out.print("이름: ");
		String userName = sc.nextLine();
		
		System.out.print("성별(M,F): ");
		String gender = sc.nextLine().toUpperCase();
		
		System.out.print("나이: ");
		String age = sc.nextLine();
		
		System.out.print("이메일: ");
		String email = sc.nextLine();
		
		System.out.print("전화번호(-빼고 입력): ");
		String phone = sc.nextLine();
		
		System.out.print("주소: ");
		String address = sc.nextLine();
		
		System.out.print("취미(,로 이어서 작성): ");
		String hobby = sc.nextLine();
		
		// 회원추가 요청 => Controller메소드 요청
		mc.insertMember(userId, userPwd, userName, gender, age, email, phone, address, hobby);

	}
	
	// 3. 회원 아이디 검색
	
	
	// 5. 회원정보변경
	private void updateMemberStatus() {
		// userId
		// userPwd, EMAIL, Phone, Address
		System.out.println("\n=========회원 정보 변경========");
		System.out.print("변경할 계정의 아이디: ");
		String userId = sc.nextLine();
		
		System.out.print("변경할 비밀번호: ");
		String userPwd = sc.nextLine();
		
		System.out.print("변경할 이메일: ");
		String email = sc.nextLine();
		
		System.out.print("변경할 전화번호: ");
		String phone = sc.nextLine();
		
		System.out.print("변경할 주소: ");
		String address = sc.nextLine();
		
		mc.updateMemberStatus(userId, userPwd, email, phone, address);

	}
	
	// 6. 
	private void deleteMember() {
		System.out.println("\n==========회원 탈퇴=========");
		mc.deleteMember(inputMemberId());
		
	}
	
	private String inputMemberId() {
		System.out.print("탈퇴할 아이디: ");
		return sc.nextLine();
	}
	/**
	 * 서비스요청 처리 후 성공했을 경우 사용자가 보게될 응답화면
	 * @param: 객체별 성공 메시지
	 */
	public void displaySuccess(String message) {
		System.out.println("\n서비스 요청 성공: "+message);
	}
	/**
	 * 서비스요청 처리 후 실패했을 경우 사용자가 보게될 응답화면
	 * @param: 객체별 실패 메시지
	 */
	public void displayFail(String message) {
		System.out.println("\n서비스 요청 실패: "+message);
	}
	
	/**
	 * 서비스요청 처리 후 조회 결과가 여러행일 때 사용자가 보게될 응답화면
	 * @param: list: 조회결과 
	 */
	public void displayData(ArrayList<Member> list) {	
		for(Member m : list) {
			System.out.println(m);
		}
	}
	
	/**
	 * 서비스요청 처리 후 조회 결과가 없을 때 사용자가 보게될 응답화면
	 * @param: 조회결과가 없을때 메시지
	 */
	public void displayNoData(String message) {
		System.out.println("\n"+message);
	}
	
	public void displayMember(Member member) {
		System.out.println(member.toString());
	}
}
