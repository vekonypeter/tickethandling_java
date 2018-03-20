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

import hu.qwaevisz.tickethandling.ejbservice.facade.EmployeeFacade;
import hu.qwaevisz.tickethandling.ejbservice.facade.SystemFacade;
import hu.qwaevisz.tickethandling.ejbservice.facade.TicketFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.EmployeeStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.StatusStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.SystemStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.weblayer.common.Page;
import hu.qwaevisz.tickethandling.weblayer.common.TicketCreateAttribute;
import hu.qwaevisz.tickethandling.weblayer.common.TicketCreateParameter;

@WebServlet("/TicketCreate")
public class TicketCreateController extends HttpServlet implements TicketCreateParameter, TicketCreateAttribute {

	private static final long serialVersionUID = -4068275526750462197L;

	private static final Logger LOGGER = Logger.getLogger(TicketCreateController.class);

	private static final String TRUE_VALUE = "1";

	@EJB
	private TicketFacade ticFacade;

	@EJB
	private EmployeeFacade empFacade;

	@EJB
	private SystemFacade sysFacade;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Creating new Ticket");

		try {
			EmployeeStub user = this.empFacade.getEmployee(request.getUserPrincipal().getName());

			TicketStub ticket = new TicketStub();
			ticket.setProcessor(user);

			request.setAttribute(ATTR_TICKET, ticket);
			request.setAttribute(ATTR_SYSTEMS, this.sysFacade.getSystems());
			request.setAttribute(ATTR_EMPLOYEES, this.empFacade.getEmployees());

			final RequestDispatcher view = request.getRequestDispatcher(Page.TICKET_CREATE.getJspName());
			view.forward(request, response);

		} catch (FacadeException e) {
			LOGGER.error(e, e);
		}
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {

			final EmployeeStub user = this.empFacade.getEmployee(request.getUserPrincipal().getName());

			final SystemStub system = this.sysFacade.getSystem(request.getParameter(SYSTEM));
			final String sender_name = request.getParameter(SENDER_NAME);
			final String business_impact = request.getParameter(BUSINESS_IMPACT);
			final String steps_to_rep = request.getParameter(STEPS_TO_REP);
			final PriorityStub priority = PriorityStub.valueOf(request.getParameter(PRIORITY));
			final StatusStub status = StatusStub.valueOf(request.getParameter(STATUS));
			final Integer level = user.getLevel();

			final TicketStub ticket = this.ticFacade.saveTicket("", system, sender_name, priority, business_impact, steps_to_rep, level, user, status, null);

			response.sendRedirect(Page.TICKET_VIEW.getUrl() + "?id=" + ticket.getId());

		} catch (FacadeException e) {
			LOGGER.error(e, e);
		}
	}
}
