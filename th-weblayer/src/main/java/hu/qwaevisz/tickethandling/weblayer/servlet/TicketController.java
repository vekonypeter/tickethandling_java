package hu.qwaevisz.tickethandling.weblayer.servlet;

import java.io.IOException;
import java.util.Date;

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
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.MessageStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.StatusStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;
import hu.qwaevisz.tickethandling.weblayer.common.Page;
import hu.qwaevisz.tickethandling.weblayer.common.TicketAttribute;
import hu.qwaevisz.tickethandling.weblayer.common.TicketParameter;

@WebServlet("/Ticket")
public class TicketController extends HttpServlet implements TicketParameter, TicketAttribute {

	private static final long serialVersionUID = -4068275526750462197L;

	private static final Logger LOGGER = Logger.getLogger(TicketController.class);

	@EJB
	private TicketFacade ticFacade;

	@EJB
	private EmployeeFacade empFacade;

	@EJB
	private SystemFacade sysFacade;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		LOGGER.info("Get Ticket by ID (" + id + ")");

		TicketStub ticket = null;

		try {
			ticket = this.ticFacade.getTicket(id);
		} catch (final ServiceException e) {
			LOGGER.error(e, e);
		}

		request.setAttribute(ATTR_TICKET, ticket);

		final RequestDispatcher view = request.getRequestDispatcher(Page.TICKET_VIEW.getJspName());
		view.forward(request, response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Saving ticket...");

		TicketStub ticket = null;
		try {

			ticket = this.ticFacade.getTicket(request.getParameter(ID));
			EmployeeStub user = this.empFacade.getEmployee(request.getUserPrincipal().getName());
			Date currentDate = new Date();
			String message = request.getParameter(NEW_MESSAGE);

			if (message == null) {

				message = "";

				PriorityStub oldPrio = ticket.getPriority();
				StatusStub oldStat = ticket.getStatus();
				Integer oldLevel = ticket.getLevel();

				PriorityStub newPrio = PriorityStub.valueOf(request.getParameter(PRIORITY));
				StatusStub newStat = StatusStub.valueOf(request.getParameter(STATUS));
				Integer newLevel = Integer.parseInt(request.getParameter(LEVEL));

				ticket.setPriority(newPrio);
				ticket.setStatus(newStat);

				if (oldLevel != newLevel) {
					ticket.setLevel(newLevel);
					ticket.setProcessor(this.empFacade.getEmployee("UNASS"));
					message += "<br /> - level changed from " + oldLevel + " to " + newLevel;
					message += "<br /> - " + user.getName() + " has been unassigned from this ticket";
				}

				if (oldPrio != newPrio) {
					ticket.setPriority(newPrio);
					message += "<br /> - priority changed from " + oldPrio + " to " + newPrio;
				}

				if (oldStat != newStat) {
					ticket.setStatus(newStat);
					message += "<br /> - status changed from " + oldStat + " to " + newStat;
				}

				if (!message.equals("")) {
					message = "The following changes were made to this ticket:" + message;
				}
			}

			if (!message.equals("")) {
				ticket.getConversation().add(new MessageStub(user.getName(), "Customer", currentDate, message));
				ticket.setLastchanged(currentDate);
				this.ticFacade.saveTicket(ticket);
			}

		} catch (FacadeException e) {
			LOGGER.error(e, e);
		} catch (ServiceException e) {
			LOGGER.error(e, e);
		}

		response.sendRedirect(Page.TICKET_VIEW.getUrl() + "?id=" + ticket.getId());
	}

}
