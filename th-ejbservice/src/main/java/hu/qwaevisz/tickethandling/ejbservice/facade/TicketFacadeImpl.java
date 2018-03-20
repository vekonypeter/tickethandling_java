
package hu.qwaevisz.tickethandling.ejbservice.facade;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import hu.qwaevisz.tickethandling.ejbservice.converter.MessageConverter;
import hu.qwaevisz.tickethandling.ejbservice.converter.TicketConverter;
import hu.qwaevisz.tickethandling.ejbservice.domain.TicketCriteria;
import hu.qwaevisz.tickethandling.ejbserviceclient.TicketFacadeRemote;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.EmployeeStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.MessageStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.StatusStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.SystemStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;
import hu.qwaevisz.tickethandling.persistence.entity.Ticket;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Priority;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Status;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;
import hu.qwaevisz.tickethandling.persistence.service.MessageService;
import hu.qwaevisz.tickethandling.persistence.service.TicketService;

@PermitAll
@Stateless(mappedName = "ejb/ticketFacade")
public class TicketFacadeImpl implements TicketFacade, TicketFacadeRemote, Serializable {

	private static final long serialVersionUID = -884509807360941797L;

	private static final Logger LOGGER = Logger.getLogger(TicketFacadeImpl.class);

	@EJB
	private TicketService service;

	@EJB
	private MessageService msgService;

	@EJB
	private TicketConverter converter;

	@EJB
	private MessageConverter msgConverter;

	@Override
	public TicketStub getTicket(String id) throws ServiceException {
		try {
			final TicketStub stub = this.converter.to(this.service.read(id));
			List<MessageStub> conversation = this.msgConverter.to(this.msgService.readConversation(stub.getId()));
			stub.setConversation(conversation);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Ticket by id (" + id + ") --> " + stub);
			}

			return stub;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (DOMException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (ParserConfigurationException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (SAXException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (IOException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (ParseException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (FacadeException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<TicketStub> getTickets(TicketCriteria criteria) throws FacadeException {
		List<TicketStub> stubs = new ArrayList<TicketStub>();
		try {
			List<Ticket> tickets = this.service.readAll();
			List<Ticket> filtered = new ArrayList<Ticket>(tickets);

			for (Ticket ticket : tickets) {

				if (criteria.getId() != null && !criteria.getId().toLowerCase().equals(ticket.getId().toLowerCase())) {
					filtered.remove(ticket);
					continue;
				}

				if (criteria.getPriority() != null && criteria.getPriority().toString() != ticket.getPriority().toString()) {
					filtered.remove(ticket);
					continue;
				}

				if (criteria.getStatus() != null && criteria.getStatus().toString() != ticket.getStatus().toString()) {
					filtered.remove(ticket);
					continue;
				}

				if (criteria.getProcessorId() != null
						&& (ticket.getProcessor() == null || !criteria.getProcessorId().toLowerCase().equals(ticket.getProcessor().getId().toLowerCase()))) {
					filtered.remove(ticket);
					continue;
				}

				if (criteria.getSystem() != null && !criteria.getSystem().toLowerCase().equals(ticket.getSystem().getId().toLowerCase())) {
					filtered.remove(ticket);
					continue;
				}

				if (criteria.getLevel() != null && criteria.getLevel() != ticket.getLevel()) {
					filtered.remove(ticket);
					continue;
				}
			}

			stubs = this.converter.to(filtered);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Tickets by criteria (" + criteria + ") --> " + stubs.size() + " ticket(s)");
			}
		} catch (

		final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (DOMException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (ParserConfigurationException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (SAXException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (IOException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (ParseException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
		return stubs;
	}

	@Override
	public TicketStub saveTicket(TicketStub newTicket) throws FacadeException {
		return this.saveTicket(newTicket.getId(), newTicket.getSystem(), newTicket.getSender_name(), newTicket.getPriority(), newTicket.getBusiness_impact(),
				newTicket.getSteps_to_rep(), newTicket.getLevel(), newTicket.getProcessor(), newTicket.getStatus(), newTicket.getConversation());
	}

	@Override
	public TicketStub saveTicket(String id, SystemStub system, String sender_name, PriorityStub priority, String business_impact, String steps_to_rep,
			Integer level, EmployeeStub processor, StatusStub status, List<MessageStub> conversation) throws FacadeException {
		try {
			Ticket ticket = null;
			if (this.service.exists(id)) {
				ticket = this.service.update(id, Priority.valueOf(priority.name()), level, processor.getId(), Status.valueOf(status.name()));

				this.msgService.saveConversation(this.msgConverter.from(conversation, id), id);

			} else {
				ticket = this.service.create(system.getId(), sender_name, Priority.valueOf(priority.name()), business_impact, steps_to_rep, level,
						processor.getId(), Status.valueOf(status.name()));

				this.msgService.createConversation(ticket.getId());
			}
			return this.converter.to(ticket);

		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (DOMException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (ParserConfigurationException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (SAXException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (IOException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (ParseException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (TransformerException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@Override
	public void removeTicket(String id, String message) throws FacadeException, ServiceException {
		try {
			TicketStub ticket = this.getTicket(id);
			ticket.getConversation().add(new MessageStub("System", "System", new Date(), message));
			this.saveTicket(ticket);
			this.service.delete(id);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@Override
	public TicketStub createTicket(String systemId, String sender_name, PriorityStub priority, String business_impact, String steps_to_rep,
			String initialMessage) throws FacadeException {
		try {
			Ticket ticket = this.service.create(systemId, sender_name, Priority.valueOf(priority.name()), business_impact, steps_to_rep, 1, "UNASS",
					Status.NEW);
			this.msgService.createConversation(ticket.getId(), initialMessage);

			TicketStub stub = this.converter.to(ticket);

			return stub;

		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (DOMException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (ParserConfigurationException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (SAXException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (IOException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		} catch (ParseException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<TicketStub> getTicketsByProcessor(String processorId) throws ServiceException {
		try {
			final List<TicketStub> stubs = this.converter.to(this.service.readByProcessor(processorId));

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Tickets by Processor ID (" + processorId + ")");
			}

			return stubs;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (DOMException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (ParserConfigurationException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (SAXException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (IOException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (ParseException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (FacadeException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<TicketStub> getTicketsByProcessorAndLevel(String processorId, Integer level) throws ServiceException {
		try {
			final List<TicketStub> stubs = this.converter.to(this.service.readByProcessorAndLevel(processorId, level));

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Tickets by Processor ID (" + processorId + ") and Level (" + level + ")");
			}

			return stubs;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (DOMException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (ParserConfigurationException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (SAXException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (IOException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (ParseException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		} catch (FacadeException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		}
	}
}
