package com.eshwarprasad;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LogInServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userPassword = request.getParameter("password");
		LogInDao logindao = new LogInDao();
		if (logindao.credentials(userId, userPassword)) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			response.sendRedirect("/ContactUs/request");
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
