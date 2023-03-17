package com.myservlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 원래는 주소를 치고 들어갈 수 없지만 어노테이션 때문에 가능함
// @WebServlet("/Test02")

public class Test02 extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Test02의 init() 실행됨");
	}

	public void destroy() {
		System.out.println("Test02의 destroy() 실행됨");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("Hello, This is Test02");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
