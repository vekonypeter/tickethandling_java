package hu.qwaevisz.tickethandling.webservice;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.domain.TicketCriteria;
import hu.qwaevisz.tickethandling.ejbservice.exception.AdaptorException;
import hu.qwaevisz.tickethandling.ejbservice.facade.TicketFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.MessageStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;
import hu.qwaevisz.tickethandling.webservice.domain.MessageCreateRemoteStub;
import hu.qwaevisz.tickethandling.webservice.domain.TicketCreateRemoteStub;

@Stateless
public class TicketRestServiceBean implements TicketRestService {

	private static final Logger LOGGER = Logger.getLogger(TicketRestServiceBean.class);

	@EJB
	private TicketFacade facade;

	@Override
	public List<TicketStub> getTickets() throws AdaptorException, FacadeException {
		LOGGER.info("Get all Tickets");
		return this.facade.getTickets(new TicketCriteria());
	}

	@Override
	public TicketStub createTicket(TicketCreateRemoteStub ticket) throws AdaptorException, FacadeException {
		LOGGER.info("Create new Ticket");
		return this.facade.createTicket(ticket.getSystemId(), ticket.getSender_name(), ticket.getPriority(), ticket.getBusiness_impact(),
				ticket.getSteps_to_rep(), ticket.getInitialMessage());
	}

	@Override
	public List<TicketStub> getTickets(String systemid) throws AdaptorException, FacadeException {
		LOGGER.info("Get Tickets of System " + systemid);
		TicketCriteria criteria = new TicketCriteria();
		criteria.setSystem(systemid);
		return this.facade.getTickets(criteria);
	}

	@Override
	public TicketStub sendMessage(MessageCreateRemoteStub message) throws AdaptorException, FacadeException, ServiceException {
		LOGGER.info("Send new message for Ticket " + message.getTicketId());
		TicketStub ticket = this.facade.getTicket(message.getTicketId());
		ticket.getConversation().add(new MessageStub("Customer", ticket.getProcessor().getName(), new Date(), message.getMessage()));
		return this.facade.saveTicket(ticket);
	}

	@Override
	public TicketStub getTicket(String ticketId) throws AdaptorException, FacadeException, ServiceException {
		LOGGER.info("Get Ticket by ID " + ticketId);
		return this.facade.getTicket(ticketId);
	}

	@Override
	public void deleteTicket(String ticketId) throws AdaptorException, FacadeException, ServiceException {
		LOGGER.info("Remove Ticket by ID " + ticketId);
		this.facade.removeTicket(ticketId, "Deleted by Customer");
	}
}
