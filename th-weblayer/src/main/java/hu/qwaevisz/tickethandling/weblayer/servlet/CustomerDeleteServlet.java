package hu.qwaevisz.tickethandling.weblayer.servlet;

import java.io.IOException;

import javax.ejb.EJB;
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

@WebServlet("/CustomerDelete")
public class CustomerDeleteServlet extends HttpServlet implements CustomerParameter {

	private static final long serialVersionUID = -7688739575153938635L;

	private static final Logger LOGGER = Logger.getLogger(TicketDeleteServlet.class);

	@EJB
	private CustomerFacade facade;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String systemId = request.getParameter(SYSTEMID);
		LOGGER.info("Delete Customer by id (" + systemId + ")");
		try {
			this.facade.remove(systemId);
		} catch (final FacadeException e) {
			LOGGER.error(e, e);
		}
		response.sendRedirect(Page.SYSTEMLIST.getUrl());
	}

}
