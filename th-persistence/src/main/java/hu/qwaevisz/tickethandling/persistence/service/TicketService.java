package hu.qwaevisz.tickethandling.persistence.service;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.persistence.entity.Ticket;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Priority;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Status;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;

@Local
public interface TicketService {

	boolean exists(String id) throws PersistenceServiceException;

	Ticket read(String id) throws PersistenceServiceException;

	List<Ticket> readByProcessor(String processorId) throws PersistenceServiceException;

	List<Ticket> readByProcessorAndLevel(String processorId, Integer level) throws PersistenceServiceException;

	List<Ticket> readAll() throws PersistenceServiceException;

	Ticket create(String system_id, String sender_name, Priority priority, String business_impact, String steps_to_rep, Integer level, String processor_id,
			Status status) throws PersistenceServiceException;

	Ticket update(String id, Priority priority, Integer level, String processor_id, Status status) throws PersistenceServiceException;

	void delete(String id) throws PersistenceServiceException;
}
