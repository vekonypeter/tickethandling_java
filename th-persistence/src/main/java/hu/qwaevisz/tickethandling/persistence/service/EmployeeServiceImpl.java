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

import hu.qwaevisz.tickethandling.persistence.entity.Employee;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;
import hu.qwaevisz.tickethandling.persistence.parameter.EmployeeParameter;
import hu.qwaevisz.tickethandling.persistence.query.EmployeeQuery;

@Stateless(mappedName = "ejb/employeeService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class);

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
			LOGGER.debug("Check if Employee exists by ID (" + id + ")");
		}
		try {

			final Long result = this.entityManager.createNamedQuery(EmployeeQuery.COUNT_BY_ID, Long.class).setParameter(EmployeeParameter.ID, id)
					.getSingleResult();
			return result != 0;

		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error getting Employee by ID (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Employee read(String id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check Employee by ID (" + id + ")");
		}
		Employee result = null;
		try {
			result = this.entityManager.createNamedQuery(EmployeeQuery.GET_BY_ID, Employee.class).setParameter(EmployeeParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error getting Employee by ID (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public List<Employee> readAll() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Employees");
		}
		List<Employee> result = null;
		try {
			result = this.entityManager.createNamedQuery(EmployeeQuery.GET_ALL, Employee.class).getResultList();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Employees! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public List<String> readEmpLabels() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Employee labels");
		}
		List<String> result = null;
		try {
			result = this.entityManager.createNamedQuery(EmployeeQuery.GET_EMPLABELS, String.class).getResultList();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Employee labels! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

}
