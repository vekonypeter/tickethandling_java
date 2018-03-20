package hu.qwaevisz.tickethandling.weblayer.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.facade.TicketFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;

@WebServlet("/TicketPing")
public class TicketPingServlet extends HttpServlet {

	private static final long serialVersionUID = -7058255202709402208L;

	private static final Logger LOGGER = Logger.getLogger(TicketPingServlet.class);

	@EJB
	private TicketFacade facade;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get Ticket by user");
		response.setCharacterEncoding("UTF-8");
		final PrintWriter out = response.getWriter();
		try {
			final TicketStub ticket = this.facade.getTicket("AES-324-201703160515");
			out.println(ticket.toString());
		} catch (final ServiceException e) {
			LOGGER.error(e, e);
			out.println(e.getLocalizedMessage());
		}
		out.close();
	}

}
