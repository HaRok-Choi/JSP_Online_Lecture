package com.myhome.board.action;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action { // Model 구현시 execute()를 오버라이드해서 사용한다.
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, Exception;
	// 추상메서드
	// ActionForward를 리턴함
}
