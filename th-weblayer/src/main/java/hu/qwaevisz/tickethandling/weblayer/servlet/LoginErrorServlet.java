package hu.qwaevisz.tickethandling.weblayer.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.qwaevisz.tickethandling.weblayer.common.LoginAttribute;
import hu.qwaevisz.tickethandling.weblayer.common.Page;

@WebServlet("/LoginError")
public class LoginErrorServlet extends HttpServlet implements LoginAttribute {

	private static final long serialVersionUID = 1599166100486735562L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String userId = request.getParameter("j_username");

		request.setAttribute(ATTR_USERID, userId);
		request.setAttribute(ATTR_ERROR, "Login failed");

		final RequestDispatcher view = request.getRequestDispatcher(Page.LOGIN.getJspName());
		view.forward(request, response);
	}

}
