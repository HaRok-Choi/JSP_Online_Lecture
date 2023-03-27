package com.myhome.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/file/Download")
public class Download extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 넘겨받은 인자값 (파일명)
		String fileName = request.getParameter("fileName");

		// storage 폴더의 실제 경로 받아오기 
		String realFolder = request.getServletContext().getRealPath("/storage");

		// 다운 받고자 하는 파일 받아오기
		File file = new File(realFolder, fileName);

		// response 헤더 세팅
		// ex) Content-Disposition:attachement;fileName = 파일명
		//     Content-Length : 파일크기
		fileName="attachment;fileName=" + new String(URLEncoder.encode(fileName,"UTF-8")).replaceAll("\\+"," ");
		response.setHeader("Content-Disposition", fileName);
		response.setHeader("Content-Length", String.valueOf(file.length()));

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

		byte[] b = new byte[(int)file.length()]; 
		// 속도가 빨라서 byte[] 씀
		// Download.class에 byte[] 안에 file이 담김
		bis.read(b, 0, b.length); // bis의 0번 byte부터 b.length만큼 b에 복사
		// bis : storage -> Download.class
		bos.write(b);
		// bos : Download.class -> response
		
		bis.close();
		bos.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
