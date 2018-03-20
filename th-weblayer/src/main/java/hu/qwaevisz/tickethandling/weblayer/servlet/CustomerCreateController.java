package hu.qwaevisz.tickethandling.weblayer.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.facade.CustomerFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.weblayer.common.CustomerParameter;
import hu.qwaevisz.tickethandling.weblayer.common.Page;

@WebServlet("/CustomerCreate")
public class CustomerCreateController extends HttpServlet implements CustomerParameter {

	private static final long serialVersionUID = -4068275526750462197L;

	private static final Logger LOGGER = Logger.getLogger(CustomerCreateController.class);

	@EJB
	private CustomerFacade custFacade;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final RequestDispatcher view = request.getRequestDispatcher(Page.CUSTOMER_CREATE.getJspName());
		view.forward(request, response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {

			final String systemId = request.getParameter(SYSTEMID);
			final String name = request.getParameter(NAME);
			final String address = request.getParameter(ADDRESS);
			final String contact_name = request.getParameter(CONT_NAME);
			final String contact_phone = request.getParameter(CONT_PHONE);
			final String contact_mail = request.getParameter(CONT_EMAIL);

			this.custFacade.createCustomer(systemId, name, address, contact_name, contact_phone, contact_mail);

			response.sendRedirect(Page.SYSTEMLIST.getUrl());

		} catch (FacadeException e) {
			LOGGER.error(e, e);
		}
	}
}
