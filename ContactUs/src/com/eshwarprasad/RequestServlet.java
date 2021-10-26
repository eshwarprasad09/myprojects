package com.eshwarprasad;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/request")
public class RequestServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDao r = new RequestDao();
		List<Request> requests = r.getRequests();
		request.setAttribute("requests", requests);
		RequestDispatcher rd = request.getRequestDispatcher("Request.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String value = request.getParameter("active");
		int id = Integer.parseInt(request.getParameter("id"));
		RequestDao requestDao = new RequestDao();
		if (requestDao.updateStatus(value, id)) {
			response.sendRedirect("/ContactUs/request");

		} else {
			response.sendRedirect("login.jsp");
		}
	}
}
