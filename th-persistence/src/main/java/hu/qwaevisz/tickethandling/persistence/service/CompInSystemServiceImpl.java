package hu.qwaevisz.tickethandling.persistence.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.persistence.entity.CompInSystem;
import hu.qwaevisz.tickethandling.persistence.entity.Customer;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Component;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;
import hu.qwaevisz.tickethandling.persistence.parameter.CompInSystemParameter;
import hu.qwaevisz.tickethandling.persistence.query.CompInSystemQuery;

@Stateless(mappedName = "ejb/compinsystemService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CompInSystemServiceImpl implements CompInSystemService {

	private static final Logger LOGGER = Logger.getLogger(CompInSystemServiceImpl.class);

	@PersistenceContext(unitName = "th-persistence-unit")
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public CompInSystem create(Customer customer, Component component, String description) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create new Connection between System and Component");
		}
		try {
			final CompInSystem cis = new CompInSystem(customer, component, description, new Date());
			this.entityManager.persist(cis);

			return cis;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting new Connection!" + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void remove(Customer customer, Component component) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Delete Connection between System and Component");
		}
		try {
			this.entityManager.createNamedQuery(CompInSystemQuery.REMOVE_BY_SYS_COMP).setParameter(CompInSystemParameter.COMPONENT, component)
					.setParameter(CompInSystemParameter.CUSTOMER, customer).executeUpdate();

		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during deleting Connection!" + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void remove(Customer customer) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Delete Connection between System and Component");
		}
		try {
			this.entityManager.createNamedQuery(CompInSystemQuery.REMOVE_BY_SYS).setParameter(CompInSystemParameter.CUSTOMER, customer).executeUpdate();

		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during deleting Connection!" + e.getLocalizedMessage(), e);
		}

	}

	@Override
	public List<CompInSystem> getByCustomer(Customer customer) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Connections of System");
		}
		try {
			return this.entityManager.createNamedQuery(CompInSystemQuery.GET_BY_SYS, CompInSystem.class).setParameter(CompInSystemParameter.CUSTOMER, customer)
					.getResultList();

		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during deleting Connection!" + e.getLocalizedMessage(), e);
		}
	}
}
