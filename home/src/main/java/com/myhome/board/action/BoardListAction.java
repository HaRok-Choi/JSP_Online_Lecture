package com.myhome.board.action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myhome.dao.BoardDao;
import com.myhome.dto.BoardDto;

// Action 인터페이스를 구현할 클래스(BoardListAction)
public class BoardListAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = Integer.parseInt(request.getParameter("page"));
		BoardDao dao = BoardDao.getInstance();
		ArrayList<BoardDto> list = dao.getList(page); // DB에 list 정보를 받아온다.
		int totalpages = dao.getTotalPages();
		ActionForward actionForward = new ActionForward();
		request.setAttribute("list", list); // request영역으로 list 속성을 추가한다.
		request.setAttribute("totalPages", totalpages); 
		request.setAttribute("currentPage", page);
		actionForward.setNextPath("BoardListView.do"); // execute() 종료 후 BoardListView.do 요청하겠다.
		actionForward.setRedirect(false);   // forward 하겠다.
		return actionForward; 
	}
}
