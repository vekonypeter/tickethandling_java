package hu.qwaevisz.tickethandling.persistence.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.persistence.entity.Customer;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;
import hu.qwaevisz.tickethandling.persistence.parameter.CustomerParameter;
import hu.qwaevisz.tickethandling.persistence.query.CustomerQuery;

@Stateless(mappedName = "ejb/customerService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class);

	@PersistenceContext(unitName = "th-persistence-unit")
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public boolean exists(String id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check if Customer exists by ID (" + id + ")");
		}
		try {

			final Long result = this.entityManager.createNamedQuery(CustomerQuery.COUNT_BY_ID, Long.class).setParameter(CustomerParameter.ID, id)
					.getSingleResult();
			return result != 0;

		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error getting Customer by ID (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Customer read(String id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check Customer by ID (" + id + ")");
		}
		Customer result = null;
		try {
			result = this.entityManager.createNamedQuery(CustomerQuery.GET_BY_ID, Customer.class).setParameter(CustomerParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error getting Customer by ID (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public List<Customer> readAll() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Customers");
		}
		List<Customer> result = null;
		try {
			result = this.entityManager.createNamedQuery(CustomerQuery.GET_ALL, Customer.class).getResultList();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Customers! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public List<String> readSysLabels() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get customer-system labels");
		}
		List<String> result = null;
		try {
			result = this.entityManager.createNamedQuery(CustomerQuery.GET_SYSLABELS, String.class).getResultList();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching customer-system labels! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Customer create(String systemId, String name, String address, String contact_name, String contact_phone, String contact_mail)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create new Customer");
		}
		try {

			final Customer customer = new Customer(systemId, name, address, contact_name, contact_phone, contact_mail);
			this.entityManager.persist(customer);
			return customer;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting new Customer!" + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void delete(String systemId) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Delete Customer");
		}
		try {
			this.entityManager.createNamedQuery(CustomerQuery.REMOVE_BY_ID).setParameter(CustomerParameter.ID, systemId).executeUpdate();

		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during deleting Customer!" + e.getLocalizedMessage(), e);
		}
	}
}
