package hu.qwaevisz.tickethandling.weblayer.servlet;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.facade.EmployeeFacade;
import hu.qwaevisz.tickethandling.ejbservice.facade.TicketFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.EmployeeStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.MessageStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;
import hu.qwaevisz.tickethandling.weblayer.common.Page;
import hu.qwaevisz.tickethandling.weblayer.common.UserActionParameter;

@WebServlet("/UserAction")
public class UserActionController extends HttpServlet implements UserActionParameter {

	private static final long serialVersionUID = -3357857759595074308L;

	private static final Logger LOGGER = Logger.getLogger(UserActionController.class);

	private static final String TRUE_VALUE = "1";

	@EJB
	private TicketFacade ticFacade;

	@EJB
	private EmployeeFacade empFacade;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String ticket_id = request.getParameter(TICKET_ID);
		final String user_id = request.getUserPrincipal().getName();
		LOGGER.info("Perform User Action");

		try {
			TicketStub ticket = this.ticFacade.getTicket(ticket_id);
			EmployeeStub user = this.empFacade.getEmployee(user_id);

			MessageStub newMessage = new MessageStub("System", "Customer", new Date(), "");

			if (TRUE_VALUE.equals(request.getParameter(GIVE_BACK_FLAG)) && ticket.getProcessor().getId().equals(user_id)) {

				ticket.setProcessor(this.empFacade.getEmployee("UNASS"));
				newMessage.setText(user.getName() + " has gave back this ticket");
			}

			if (TRUE_VALUE.equals(request.getParameter(ASSIGN_FLAG)) && ticket.getProcessor().getId().equals("UNASS")) {

				ticket.setProcessor(user);
				newMessage.setText(user.getName() + " has been assigned as processor to this ticket");
			}

			ticket.getConversation().add(newMessage);
			this.ticFacade.saveTicket(ticket);

		} catch (

		final FacadeException e) {
			LOGGER.error(e, e);
		} catch (ServiceException e) {
			LOGGER.error(e, e);
		}

		response.sendRedirect(Page.HOME.getUrl());
	}

}
