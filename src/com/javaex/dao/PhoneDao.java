package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;

public class PhoneDao {
		//필드
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		
		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "phonedb";
		private String pw = "phonedb";
		//생성자
		//메소드 gs
		//메소드 일반
		
		//DB연결 메소드
		private void getConnection() {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName(driver);

				// 2. Connection 얻어오기			
					conn = DriverManager.getConnection(url, id, pw);
			}
			catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} 
			catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		//자원정리 메소드
		private void close() {
			try {
				if (rs != null) {				
					rs.close();
				} 
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} 
			catch (SQLException e) {
					System.out.println("error:" + e);
			}
		}	
		
		//전체 출력하기 메소드
		public List<PersonVo> personSelect(){
			
			//리스트로 만들기
			List<PersonVo> personList = new ArrayList<PersonVo>();

			getConnection();
			
			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				//SQL문 준비
				String query = "";
				
				query += " select person_id, ";
				query += " 		  name, ";
				query += " 		  hp, ";
				query += " 		  company ";
				query += " from person ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				
				//실행
				//RsultSet가져오기
				rs = pstmt.executeQuery();
				
				// 4.결과처리
				//반복문으로 Vo만들기 list에 추가
				while(rs.next()) {
					int personId = rs.getInt(1);
					String name = rs.getString(2);
					String hp = rs.getString(3);
					String company = rs.getString(4);
					
					PersonVo allVo = new PersonVo(personId, name, hp, company);
					
					personList.add(allVo);
				}
			}
			catch (SQLException e) {
				System.out.println("error:" + e);
			} 
				close();
				return personList;
			}
		
		//사람 등록하기 메소드
		public int personInsert(PersonVo personVo) {
			
			int count = -1;
			getConnection();
			
			try {
			// 3. SQL문 준비 / 바인딩 / 실행
				//SQL문 준비
				String query = "";
				query += " insert into person ";
				query += " values(seq_person_id.nextval, ?, ?, ?) ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, personVo.getName());
				pstmt.setString(2, personVo.getHp());
				pstmt.setString(3, personVo.getCompany());
				
				//실행
				count = pstmt.executeUpdate();
				
				// 4.결과처리
				System.out.println("["+ count + "건 등록되었습니다.]");
			}
			catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return count;
		}
		
		//사람 수정하기 메소드
		public int personUpdate(PersonVo personVo) {
			int count = -1;
			
			getConnection();
			
			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				// SQL문 준비
				String query = "";
				query += " update person ";
				query += " set name = ?, ";
				query += "     hp = ?, ";
				query += "     company = ? ";
				query += " where person_id = ? ";
				
				
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, personVo.getName());
				pstmt.setString(2, personVo.getHp());
				pstmt.setString(3, personVo.getCompany());
				pstmt.setInt(4, personVo.getPersonId());
				
				//실행
				count = pstmt.executeUpdate();
				
				//결과처리
				System.out.println("[" + count + "건 수정되었습니다.]");
			}	
			catch (SQLException e) {
				System.out.println("error:" + e);
			}
					
			close();
			return count;
		  
		}
		
		//사람 삭제하기 메소드
		public int personDelete(int personId) {
			int count = -1;
		
				getConnection();
				
				try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
				//SQL문 준비
				String query = "";
				query += " delete from person ";
				query += " where person_id = ? ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, personId);
				
				//실행
				count = pstmt.executeUpdate();
				
				// 4.결과처리
				System.out.println("[" + count + "건 삭제되었습니다.]");
				}
				catch (SQLException e) {
					System.out.println("error:" + e);
				} 
				
			close();	
			return count;
		}
		
		//1명 정보 가져오기
		 public PersonVo getPerson(int personId) {
		      PersonVo personVo = null;
		      
		      this.getConnection();
		      
		      try {

		         // 3. SQL문 준비 / 바인딩 / 실행
		         //SQL문 준비 
		         String query = "";
		         query += " select  person_id, ";
		         query += "         name, ";
		         query += "         hp, ";
		         query += "         company ";
		         query += " from person ";
		         query += " where person_id = ? ";
		      
		         //바인딩 
		         pstmt = conn.prepareStatement(query);
		         pstmt.setInt(1, personId);
		         
		         //실행
		         rs = pstmt.executeQuery();
		         
		         // 4.결과처리
		         while(rs.next()) {
		            
		            int id = rs.getInt("person_id");
		            String name = rs.getString("name");
		            String hp = rs.getString("hp");
		            String company = rs.getString("company");
		            
		            personVo = new PersonVo(id, name, hp, company);
		         }
		         
		      } catch (SQLException e) {
		         System.out.println("error:" + e);
		      } 
		      
		      this.close();
		      
		      return personVo;
		   }
		
		
		
		
		
}