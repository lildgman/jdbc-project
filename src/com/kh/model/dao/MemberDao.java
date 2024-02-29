package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

/*
 * DAO(Data Access Object): db에 직접적으로 접근해서 사용자의 요청에 맞는 sql문 실행 후 결과 반환 (JDBC)
 * 
 * 
 */
public class MemberDao {

	/**
	 * 사용자가 입력한 정보들을 db에 추가시켜주는 메서드
	 * @param member: 사용자가 입력한 값들이 담겨있는 member 객체
	 * @return:insert문 실행 후 행의 개수
	 */
	public int insertMember(Member member) {		
		// insert문 -> 처리된 행의 수(int) -> 트랜잭션 처리
		
		// 필요한 변수를 먼저 세팅
		int result = 0; // 처리된 결과(처리된 행수)를 받아줄 변수
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbId = "JDBC" ;
		String dbPwd = "JDBC";
		
		// 실행할 sql문
//		INSERT INTO MEMBER
//		VALUES(SEQ_USERNO.NEXTVAL, 'user01','pwd01','홍길동',null,23,'user01@iei.or.kr','01022222222','부산', '등산','2021-08-07');
		
//		String sql = "INSERT INTO MEMBER VALUES (SEQ_USERNO.NEXTVAL,"
//				+ "'" + member.getUserId() + "',"
//				+ "'" + member.getUserPwd() + "',"
//				+ "'" + member.getUserName() + "',"
//				+ "'" + member.getGender() + "',"
//				+  member.getAge() + ","
//				+ "'" + member.getEmail() + "',"
//				+ "'" + member.getPhone() + "',"
//				+ "'" + member.getAddress() + "',"
//				+ "'" + member.getHobby() + "',"
//				+ "SYSDATE)";
		
		String sql = "INSERT INTO MEMBER VALUES (SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?,SYSDATE)";
		
		
		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection(dbUrl,dbId, dbPwd);
			
			// 3) Statement 객체 생성
			pstmt = conn.prepareStatement(sql); // 아직 미완성sql문으로 ? 를 전부 채워줘야한다.
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			pstmt.setString(3, member.getUserName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			
			// 4,5) sql문 전달하면서 실행 후 값 받아오기
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 jdbc 객체 반환
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
				
		try {
			conn = DriverManager.getConnection(dbUrl,dbId, dbPwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * 회원 List를 반환하는 메서드
	 * @return: Member객체가 담겨있는 List 반환
	 */
	public ArrayList<Member> selectMemberList() {
		// select문(여러 행 조회) -> ResultSet에는 여러개가 담겨져있다. -> ArrayList<Member>
		ArrayList<Member> result = new ArrayList<Member>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; //select문 실행 시 조회된 결과값들이 최초로 담기는 공간
		
		// 실행할 sql
		String sql = "SELECT * FROM MEMBER";
		
		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbId = "JDBC" ;
		String dbPwd = "JDBC";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(dbUrl,dbId, dbPwd);
			
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				Member member = new Member(rset.getInt("USERNO"),
						rset.getString("USERID"),
						rset.getString("USERPWD"),
						rset.getString("USERNAME"),
						rset.getString("GENDER"),
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("PHONE"),
						 rset.getString("ADDRESS"),
						 rset.getString("HOBBY"), 
						 rset.getDate("ENROLLDATE")
						);
				result.add(member);			
			}
			
			// 반복문이 끝난 시점
			// 조회된 데이터가 없다면 리스트는 비어있을 것이고,
			// 조회된 데이터가 있다면 전부 list에 담겨 있을 것이다.
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		return result;
	}

	
	/**
	 * 사용자가 입력한 정보들을 업데이트
	 * @param member
	 */
	public int updateMemberStatus(Member member) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbId = "JDBC" ;
		String dbPwd = "JDBC";
		
		String sql = "UPDATE MEMBER "
				+ "SET USERPWD = ?, "
				+ "EMAIL = ?, "
				+ "PHONE = ?, "
				+ "ADDRESS = ? "
				+ "WHERE USERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(dbUrl,dbId, dbPwd);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getUserPwd());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getUserId());
			
			result = pstmt.executeUpdate();
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}		
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int deleteMember(String inputMemberId) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbId = "JDBC" ;
		String dbPwd = "JDBC";
		
		String sql = "DELETE FROM MEMBER "
				+ "WHERE USERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(dbUrl,dbId,dbPwd);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputMemberId);
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}	
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}


}
