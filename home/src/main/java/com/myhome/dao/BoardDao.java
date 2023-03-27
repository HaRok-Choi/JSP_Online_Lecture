package com.myhome.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.myhome.dto.BoardDto;

public class BoardDao {
	private static BoardDao dao; 
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	static private DataSource ds; 
	static { 
		try {
			System.out.println("start DBCP!");
			Context context = new InitialContext(); 
			ds = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
//	싱글톤 패턴
	private BoardDao() {}
	public static BoardDao getInstance() {
		if (dao == null) {
			dao = new BoardDao();
		}
		return dao;
	}
	
	// close하는 메소드1
	private static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(con != null) con.close(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// close하는 메소드2 (오버로드)
	private static void close(Connection con, PreparedStatement ps) {
		close(con, ps, null);
	}
	
	// 게시글 추가 메소드
	public boolean insert(BoardDto dto) {
		boolean result = false;
		sql = "INSERT INTO board VALUES(board_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE)";
													// 글제목, 글본문, 작성자ID, 작성자NICK
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql); 
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getWriter());
			ps.setString(4, dto.getNickname());
			result = ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
		return result; 
	}
	
		// 게시물 목록 메소드 
		public ArrayList<BoardDto> getList(int page){
			ArrayList<BoardDto> list = new ArrayList<BoardDto>();
			BoardDto dto = null;
			int start = page * 5 - 4;
			int end = page * 5;
			
			sql = "SELECT num, title, content, nickname, hit, regdate FROM "
				+ "(SELECT ROWNUM rn, tt.* FROM "
				+ "(SELECT * FROM board ORDER BY num DESC) tt) "
				+ "WHERE rn >=? AND rn <=?";

			try {
				con = ds.getConnection();
				ps = con.prepareStatement(sql);
				ps.setInt(1, start);
				ps.setInt(2, end);
				rs = ps.executeQuery();
				while(rs.next()) {
					dto = new BoardDto();
					dto.setNum( rs.getInt("num" )); // 게시물 번호
					dto.setTitle( rs.getString("title" )); // 글 제목
					dto.setNickname( rs.getString("nickname")); // 작성자 닉네임
					dto.setHit( rs.getInt("hit")); // 조회수
					dto.setRegdate( rs.getString("regdate")); // 등록일자
					list.add(dto);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				close(con, ps, rs);
			}
			return list.isEmpty() ? null : list;
		}
		
		// 게시물 목록 전체 페이지 수 메서드
		public int getTotalPages() {
			int total = 0;
			sql = "SELECT COUNT(*) FROM board";
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next()) {
					total = rs.getInt("COUNT(*)");
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				close(con, ps, rs);
			}
			return (total-1)/5 + 1;
		}
	
	// 게시물 본문 조회 메소드
	public BoardDto select(int num){
		BoardDto dto = null;
		sql = "SELECT * FROM board WHERE num = ?"; 
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new BoardDto();
				dto.setNum( rs.getInt("num" )); 			// 게시글 번호
				dto.setTitle( rs.getString("title" )); 		// 글 제목
				dto.setContent( rs.getString("content")); 	// 글 본문
				dto.setWriter( rs.getString("writer")); 	// 작성자ID
				dto.setNickname( rs.getString("nickname")); // 작성자닉네임
				dto.setHit( rs.getInt("hit")); 				// 조회수
				dto.setRegdate( rs.getString("regdate")); 	// 등록일자
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return dto;
	}
	
	// 게시물 수정 메소드
	public boolean update(int num, String newTitle, String newContent) {
		boolean result = false;
		sql = "UPDATE board SET title = ?, content = ? WHERE num = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, newTitle);
			ps.setString(2, newContent);
			ps.setInt(3, num);
			result = ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return result;
	}
	
	// 게시물 삭제 메소드
	public boolean delete(int num) {
		boolean result = false;
		sql = "DELETE FROM board WHERE num = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			result = ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return result;
	}
	
	// 조회수 1 증가 메소드
	public boolean updateHit(int num) {
		boolean result = false;
		sql = "UPDATE board SET hit = hit + 1 WHERE num = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			result = ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return result;
	}
}
