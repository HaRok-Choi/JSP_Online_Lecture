package com.myhome.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.myhome.dto.MemberDto;

public class MemberDao {
//	private String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
//	private String db_username = "myJsp";
//	private String db_password = "jsppassword";
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	static private DataSource ds;
//	DataSource : Connection Pool안에 있는 관리자라고 생각하면 됨.
//	커넥션을 빌리고, 돌려주고를 전부 DataSource를 통해서 함
	static {
		try {
			System.out.println("start DBCP!");
			Context context = new InitialContext(); // 현재 웹어플리케이션에 Context를 얻어옴
			ds = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
//			lookup은 JNDI인터페이스에 소속된 API이고, 
//			커넥션풀을 가지고 오겠다라는 뜻
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean insert(MemberDto dto) {
		boolean result = false;
		sql = "INSERT INTO member VALUES(member_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
		try {
			con = ds.getConnection();	// ds에서 커넥션을 하나 빌려 옴
			ps.setString(1, dto.getId() );
			ps.setString(2, dto.getPassword() );
			ps.setString(3, dto.getEmail());
			ps.setString(4, dto.getNickname() );
			result = ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps!=null && con!=null) {
					ps.close();
					con.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public MemberDto select(String id, String password) {
		MemberDto dto = null;
		sql = "SELECT * FROM member WHERE id = ? AND password = ?";
		try {
			con = ds.getConnection();
			ps.setString(1, id);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				dto = new MemberDto();
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				dto.setEmail(rs.getString("email"));
				dto.setNickname(rs.getString("nickname"));
				dto.setNo(rs.getInt("no"));
				dto.setRegdate(rs.getString("regdate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && ps != null && con != null) {
					rs.close();
					ps.close();
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
//	아이디 찾기
	public String findIdbyEmail(String email) {
		String id = null;
		sql = "SELECT id FROM member WHERE email = ?";
		try {
			con = ds.getConnection();
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getString("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && ps != null && con != null) {
					rs.close();
					ps.close();
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
//	회원탈퇴
	public boolean delete(String id, String password) {
		boolean result = false;
		sql = "DELETE FROM member WHERE id = ? AND password = ?";
		try {
			con = ds.getConnection();
			ps.setString(1, id);
			ps.setString(2, password);
			result = ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && ps != null && con != null) {
					rs.close();
					ps.close();
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
//	아이디 중복 확인
	public boolean isExistId(String id) {
		boolean exist = false;
		String sql = "SELECT * FROM member WHERE id = ?";
		try {
			con = ds.getConnection();
			ps.setString(1, id);
			rs = ps.executeQuery();
			exist = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && ps != null && con != null) {
					rs.close();
					ps.close();
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return exist;
	}
	
//	수정작업
	public boolean modify(MemberDto dto) {
		sql = "UPDATE member SET password = ?, email = ?, nickname = ? WHERE no = ?";
		int result = 0;
		try {
			con = ds.getConnection();
			ps.setString(1, dto.getPassword());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getNickname());
			ps.setInt(4, dto.getNo());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result == 1;
	}
	
//	전회원 정보 확인
	public ArrayList<MemberDto> getMemberList() {
		ArrayList<MemberDto> list = new ArrayList<MemberDto>();
		MemberDto dto = null;
		String tmpPassword;
		int passwordIndex = 0;
		sql = "SELECT * FROM member";
		try {
			con = ds.getConnection();
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new MemberDto();
				
				// 비밀번호는 앞 두 글자만 보여주기(나머지 '*' 처리)
				tmpPassword = rs.getString("password");
				passwordIndex = tmpPassword.length() - 2;
				tmpPassword = tmpPassword.substring(0,2); 
				while(passwordIndex > 0) {
					tmpPassword += "*";
					--passwordIndex;
				}
				
				// dto에 데이터 세팅
				dto.setNo( rs.getInt("no") );
				dto.setId( rs.getString("id") );
				dto.setNickname( rs.getString("nickname") );
				dto.setPassword( tmpPassword );
				dto.setEmail( rs.getString("email") );
				dto.setRegdate( rs.getString("regdate") );
				
				// list에 dto 추가
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list.isEmpty() ? null : list;  // list를 return 하되 list의 원소가 없다면 null을 return
	}
}
