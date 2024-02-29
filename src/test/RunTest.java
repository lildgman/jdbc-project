package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RunTest {
	/*
	 * JDBC를 사용하기 위해서 자바 프로젝트에 jdbc드라이버를 추가해야 함
	 * 
	 * JDBC용 객체
	 * - Connection: DB의 연결정보를 담고 있는 객체
	 * - [Prepared]Statement: 연결된 DB에 sql문을 전달해서 실행하고, 결과를 받아내는 객체
	 * - ResultSet: SELECT 실행 후 조회된 결과를 담는 객체
	 * 
	 * 
	 * JDBC 과정(순서중요)
	 * 1) JDBC Driver 등록: 해당 DBMS(오라클)가 제공하는 클래스 등록
	 * 2) Connection 생성: 연결하고자 하는 db정보를 입력해서 해당 db와 연결할 수 있는 객체 생성
	 * 3) Statement객체 생성: Connection 객체를 이용해서 생성(sql문 실행 및 결과를 받는 객체)
	 * 4) SQL문 전달하면서 실행: Statement객체를 이용해 sql문 실행
	 * 5) 결과받기 > select문 실행(6-1) => ResultSet 객체(조회된 결과를 담아준다)
	 * 			> DML(6-2)(INSERT, UPDATE, DELETE)은 실행 => int로 받아줌
	 * 
	 * 6-1)
	 * ResultSet 객체에 담겨있는 데이터들을 하나씩 추출해서 차근차근 옮겨 담기[+ArrayList에 담기]
	 * 
	 * 6-2) 트랜잭션 처리(성공했다면 COMMIT, 실패했다면 ROLLBACK 실행)
	 * 
	 * 7) 다 사용한 JDBC용 객체를 반드시 자원 반납해줘야 한다(close) => 생성된 역순으로
	 * 
	 */

	
	public static void main(String[] args) {
		/*
		// 1. 각자 pc(localhost)에 jdbc계정 연결 후 test테이블에 insert 해보자.
		// insert => 처리된 행 수(int) 반환 => 트랜잭션 처리
		
		// 필요한 변수 세팅
		int result = 0; // 결과(처리된 행 수)를 받아줄 변수
		Connection conn = null; // DB의 연결 정보를 보관할 객체
		Statement stmt = null; // sql문을 전달해서 실행 후 결과를 받는 객체
		
		// 실행할 sql문(완전한 상태로 만들어두기) (맨 뒤에 ; 없어야한다!)
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("번호: ");
		int num = sc.nextInt();
		sc.nextLine();
		
		System.out.println("이름: ");
		String name = sc.nextLine();
		
		String sql = "INSERT INTO TEST VALUES("+num+",'"+ name +"',SYSDATE)";
		
		
		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver"); //ojdbc6.jar 파일 추가했는지 확인
			System.out.println("OracleDriver등록 성공!");
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String userId = "JDBC" ;
			String userPwd = "JDBC";
			
			// 2) Connection생성(url, 계정명, 비밀번호)
			conn = DriverManager.getConnection(url,userId,userPwd);
			
			// 3) Statement객체 생성
			stmt= conn.createStatement();
			// 4,5) sql문 전달하면서 실행 후 결과 받기(처리된 행 수)
			result = stmt.executeUpdate(sql);
			// 내가 실행할 sql문이 dml문(INSERT, UPDATE, DELETE)일 때 -> stmt.executeUpdate(sql문) : int
			// 내가 실행할 sql문이 select문 -> stmt.executeQuery(select문): ResultSet
			
			
			// 6) 트랜잭션 처리
			if(result > 0) { // 요청 sql문 성공
				conn.commit();
			} else { // 요청 sql문 실패
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7) 다 쓴 jdbc 객체 자원 반납(생성 역순으로)
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		
		if(result > 0) { // 요청 sql문 성공
			System.out.println("데이터 삽입 성공");
		} else { // 요청 sql문 실패
			System.out.println("데이터 삽입 실패");
		}
		*/		
		
		// 2. 내 pc db상에 jdbc계정에 Test테이블에 모든 데이터 조회해보기
		// select문 => ResultSet으로 나옴(조회된 데이터가 담겨있음)
		// ResultSet으로부터 데이터 추출
		
		// 필요한 변수 세팅
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userId = "JDBC" ;
		String userPwd = "JDBC";
		
		// 실행할 SQL문
		String sql = "SELECT * FROM TEST";
		
		try {
			
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("OracleDriver등록 성공!");
			
			// 2) Connection생성(url, 계정명, 비밀번호)
			conn = DriverManager.getConnection(url,userId,userPwd);
			
			// 3) Statement객체 생성
			stmt= conn.createStatement();
			
			// 4, 5) SQL문 전달해서 실행 후 결과 받기(ResultSet)
			rset = stmt.executeQuery(sql);
			
			// rset.next() => rset의 다음행이 들어있는지 없는지 확인 후 결과반환 + 한행을 내려준다.
			// 처음엔 해드를 가리키고 있다.
			
			while(rset.next()) {
				// 현재 참조하는 rset으로부터 어떤 컬럼에 해당하는 값을 어떤 타입으로 추출할건지 제시해줘야한다.
				// db의 컬럼명 제시(대소문자 구분하지 않는다.)
				int tno = rset.getInt("TNO");
				String tname = rset.getString("TNAME");
				Date tdate = rset.getDate("TDATE");
				
				System.out.println(tno + ", " + tname + ", " + tdate);
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7) 다 쓴 jdbc 객체 자원 반납(생성 역순으로)
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
