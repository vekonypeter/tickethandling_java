
package hu.qwaevisz.tickethandling.ejbservice.facade;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.converter.EmployeeConverter;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.EmployeeStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;
import hu.qwaevisz.tickethandling.persistence.service.EmployeeService;

@PermitAll
@Stateless(mappedName = "ejb/employeeFacade")
public class EmployeeFacadeImpl implements EmployeeFacade {

	private static final Logger LOGGER = Logger.getLogger(EmployeeFacade.class);

	@EJB
	private EmployeeService empService;

	@EJB
	private EmployeeConverter empConverter;

	@Override
	public EmployeeStub getEmployee(String id) throws FacadeException {
		try {
			final EmployeeStub stub = this.empConverter.to(this.empService.read(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Employee by id (" + id + ") --> " + stub);
			}
			return stub;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<EmployeeStub> getEmployees() throws FacadeException {
		try {
			List<EmployeeStub> stubs = this.empConverter.to(this.empService.readAll());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Employees");
			}
			return stubs;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<String> getEmpLabels() throws FacadeException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Employee labels");
			}
			List<String> labels = this.empService.readEmpLabels();
			return labels;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

}
