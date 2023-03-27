package com.myhome.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myhome.dao.MemberDao;
import com.myhome.dto.MemberDto;

@WebServlet("/login/loginLogic.jsp")
public class Login extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("user_id");
		String password = request.getParameter("user_password");
		
		MemberDao dao = new MemberDao();
		MemberDto dto = dao.select(id, password);
		
		boolean rememberMe = Boolean.parseBoolean(request.getParameter("remember_me"));
//		아이디가 쿠키에 저장되긴 하지만 보안에 취약하기 때문에 실무에서는 이렇게 하지 못한다.
		if(rememberMe) {
			Cookie cookie = new Cookie("rememberId", id);
			cookie.setMaxAge(60 * 60 * 24 * 365); // 1년
			cookie.setPath("/"); // 선택사항, 있어도 되고 없어도 된다.
			response.addCookie(cookie);
		} else { // '아이디 기억하기'가 체크 안되어있을 경우, 기존에 있을 수 있는 쿠키 삭제
			Cookie cookie = new Cookie("rememberid", null); // rememberId라는 임시쿠키를 만든다.
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie); // 기존 쿠키 덮어쓰기
		}
		
		if(dto != null){
//			request.setAttribute("nickname", dto.getNickname());
//			JSP에서는 내장객체에 session이 존재하지만 servlet에는 존재하지 않는다.
			HttpSession session = request.getSession(); // request객체에서 session을 얻어올 수 있다.
			session.setAttribute("currentNickname", dto.getNickname());
		}
		RequestDispatcher rd = request.getRequestDispatcher("loginResultView.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
