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
import com.myhome.dto.CommentDto;

public class CommentDao {
	private static CommentDao dao;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	private static DataSource ds;
	
	static {
		try {
			Context context = new InitialContext();
			ds = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private CommentDao() {}

	public static CommentDao getInstance() {
		if (dao == null) {
			dao = new CommentDao();
		}
		return dao;
	}

	private static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void close(Connection con, PreparedStatement ps) {
		close(con, ps, null);
	}

	public boolean insert(CommentDto dto) {
		boolean result = false;
		sql = "INSERT INTO comments VALUES( comment_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getParentNum());
			ps.setString(2, dto.getComment());
			ps.setString(3, dto.getId());
			ps.setString(4, dto.getNickname());
			result = ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
		return result;
	}

	// 댓글 목록
	public ArrayList<CommentDto> getList(int parentNum) {
		ArrayList<CommentDto> list = new ArrayList<CommentDto>();
		CommentDto dto = null;
		sql = "SELECT * FROM comments WHERE parentNum = ? ORDER BY num ASC" ;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, parentNum);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CommentDto();
				dto.setNum(rs.getInt("num")); // 댓글 고유 번호
				dto.setParentNum(rs.getInt("parentNum")); // 댓글의 출신 게시글
				dto.setComment(rs.getString("comment")); // 댓글 내용
				dto.setId(rs.getString("id")); // 댓글 작성자 id
				dto.setNickname(rs.getString("nickname")); // 댓글 작성자 nickname
				dto.setRegdate(rs.getString("regdate")); // 등록일자
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return list.isEmpty() ? null : list;
	}

	public boolean update(int num, String newComment) {
		boolean result = false;
		sql = "UPDATE comments SET comment = ? WHERE num = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, newComment + " (������)");
			ps.setInt(2, num);
			result = ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return result;
	}

	public boolean delete(int num) { 
		boolean result = false;
		sql = "DELETE FROM comments WHERE num = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			result = ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return result;
	}
}
