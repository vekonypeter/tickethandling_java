package hu.qwaevisz.tickethandling.weblayer.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.facade.TicketFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;
import hu.qwaevisz.tickethandling.weblayer.common.Page;
import hu.qwaevisz.tickethandling.weblayer.common.TicketParameter;

@WebServlet("/TicketDelete")
public class TicketDeleteServlet extends HttpServlet implements TicketParameter {

	private static final long serialVersionUID = -7688739575153938635L;

	private static final Logger LOGGER = Logger.getLogger(TicketDeleteServlet.class);

	@EJB
	private TicketFacade facade;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		LOGGER.info("Delete Ticket by id (" + id + ")");
		try {
			this.facade.removeTicket(id, "Deleted by " + request.getUserPrincipal().getName());
		} catch (final FacadeException e) {
			LOGGER.error(e, e);
		} catch (ServiceException e) {
			LOGGER.error(e, e);
		}
		response.sendRedirect(Page.HOME.getUrl());
	}

}
