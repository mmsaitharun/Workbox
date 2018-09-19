package com.workbox.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TestingServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		DataClass dataClass = new DataClass();
//		ServicesDataClass servicesDataClass = new ServicesDataClass();
//		response.getWriter().write("Workflow Data : "+dataClass.getData() + " Services Data : "+servicesDataClass.getData());
	}
}
