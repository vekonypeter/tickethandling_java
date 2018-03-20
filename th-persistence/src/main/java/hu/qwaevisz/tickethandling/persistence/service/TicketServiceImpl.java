package hu.qwaevisz.tickethandling.persistence.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.persistence.entity.Ticket;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Priority;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Status;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;
import hu.qwaevisz.tickethandling.persistence.parameter.TicketParameter;
import hu.qwaevisz.tickethandling.persistence.query.TicketQuery;

@Stateless(mappedName = "ejb/ticketService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TicketServiceImpl implements TicketService {

	private static final Logger LOGGER = Logger.getLogger(TicketServiceImpl.class);

	@PersistenceContext(unitName = "th-persistence-unit")
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@EJB
	private CustomerService customerService;

	@EJB
	private EmployeeService employeeService;

	@Override
	public boolean exists(String id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check Ticket by ID (" + id + ")");
		}
		try {
			final Long result = this.entityManager.createNamedQuery(TicketQuery.COUNT_BY_ID, Long.class).setParameter(TicketParameter.ID, id).getSingleResult();
			return result != 0;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error getting Ticket by ID (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Ticket read(String id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Ticket by id (" + id + ")");
		}
		Ticket result = null;
		try {
			result = this.entityManager.createNamedQuery(TicketQuery.GET_BY_ID, Ticket.class).setParameter(TicketParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Ticket by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public List<Ticket> readAll() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Tickets");
		}
		List<Ticket> result = null;
		try {
			result = this.entityManager.createNamedQuery(TicketQuery.GET_ALL, Ticket.class).getResultList();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Tickets! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Ticket create(String system_id, String sender_name, Priority priority, String business_impact, String steps_to_rep, Integer level,
			String processor_id, Status status) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create new Ticket");
		}
		try {
			final DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			final Date currentDate = new Date();
			final String id = (system_id + format.format(currentDate)).replace("-", "").replace(" ", "");

			final Ticket ticket = new Ticket(id, this.customerService.read(system_id), sender_name, priority, business_impact, steps_to_rep, currentDate, level,
					this.employeeService.read(processor_id), status, currentDate);
			this.entityManager.persist(ticket);
			return ticket;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting new Ticket!" + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Ticket update(String id, Priority priority, Integer level, String processor_id, Status status) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Ticket");
		}
		try {
			final Ticket ticket = this.read(id);

			ticket.setPriority(priority);
			ticket.setProcessor(this.employeeService.read(processor_id));
			ticket.setLastchanged(new Date());
			ticket.setLevel(level);
			ticket.setStatus(status);

			return ticket;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when updating Ticket! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void delete(String id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove Ticket by id (" + id + ")");
		}
		try {
			this.entityManager.createNamedQuery(TicketQuery.REMOVE_BY_ID).setParameter(TicketParameter.ID, id).executeUpdate();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when removing Ticket by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<Ticket> readByProcessor(String processorId) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Tickets by Processor (" + processorId + ")");
		}
		List<Ticket> result = null;
		try {
			result = this.entityManager.createNamedQuery(TicketQuery.GET_BY_PROCESSOR, Ticket.class).setParameter(TicketParameter.PROCESSOR, processorId)
					.getResultList();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Tickets by Processor (" + processorId + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public List<Ticket> readByProcessorAndLevel(String processorId, Integer level) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Tickets by Processor (" + processorId + ") and Level (" + level + ")");
		}
		List<Ticket> result = null;
		try {
			result = this.entityManager.createNamedQuery(TicketQuery.GET_BY_LEVEL_AND_PROCESSOR, Ticket.class)
					.setParameter(TicketParameter.PROCESSOR, processorId).setParameter(TicketParameter.LEVEL, level).getResultList();
		} catch (final Exception e) {
			throw new PersistenceServiceException(
					"Unknown error when fetching Tickets by Processor (" + processorId + ") and Level (" + level + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

}
