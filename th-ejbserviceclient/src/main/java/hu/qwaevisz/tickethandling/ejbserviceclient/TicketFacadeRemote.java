package hu.qwaevisz.tickethandling.ejbserviceclient;

import javax.ejb.Remote;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;

@Remote
public interface TicketFacadeRemote {

	TicketStub getTicket(String id) throws ServiceException;

}
