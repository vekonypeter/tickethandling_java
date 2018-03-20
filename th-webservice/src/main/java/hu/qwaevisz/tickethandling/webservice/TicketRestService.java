package hu.qwaevisz.tickethandling.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import hu.qwaevisz.tickethandling.ejbservice.exception.AdaptorException;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;
import hu.qwaevisz.tickethandling.webservice.domain.MessageCreateRemoteStub;
import hu.qwaevisz.tickethandling.webservice.domain.TicketCreateRemoteStub;

@Path("/ticket")
public interface TicketRestService {

	@GET
	@Path("/all")
	@Produces("application/json")
	List<TicketStub> getTickets() throws AdaptorException, FacadeException;

	@GET
	@Path("/{ticketId}")
	@Produces("application/json")
	TicketStub getTicket(@PathParam("ticketId") String ticketId) throws AdaptorException, FacadeException, ServiceException;

	@GET
	@Path("/system/{systemid}")
	@Produces("application/json")
	List<TicketStub> getTickets(@PathParam("systemid") String systemid) throws AdaptorException, FacadeException;

	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/json")
	TicketStub createTicket(TicketCreateRemoteStub ticket) throws AdaptorException, FacadeException;

	@PUT
	@Path("/send")
	@Consumes("application/json")
	TicketStub sendMessage(MessageCreateRemoteStub message) throws AdaptorException, FacadeException, ServiceException;

	@DELETE
	@Path("/{ticketId}")
	void deleteTicket(@PathParam("ticketId") String ticketId) throws AdaptorException, FacadeException, ServiceException;
}
