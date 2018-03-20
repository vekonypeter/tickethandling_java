package hu.qwaevisz.tickethandling.ejbservice.facade;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.ejbservice.domain.TicketCriteria;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.EmployeeStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.MessageStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.StatusStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.SystemStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;

@Local
public interface TicketFacade {

	TicketStub getTicket(String id) throws ServiceException;

	List<TicketStub> getTicketsByProcessorAndLevel(String processorId, Integer level) throws ServiceException;

	List<TicketStub> getTicketsByProcessor(String processorId) throws ServiceException;

	List<TicketStub> getTickets(TicketCriteria criteria) throws FacadeException;

	TicketStub saveTicket(TicketStub ticket) throws FacadeException;

	TicketStub saveTicket(String id, SystemStub system, String sender_name, PriorityStub priority, String business_impact, String steps_to_rep, Integer level,
			EmployeeStub processor, StatusStub status, List<MessageStub> conversation) throws FacadeException;

	TicketStub createTicket(String systemId, String sender_name, PriorityStub priority, String business_impact, String steps_to_rep, String initialMessage)
			throws FacadeException;

	void removeTicket(String id, String message) throws FacadeException, ServiceException;

}
