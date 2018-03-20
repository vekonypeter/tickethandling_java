
package hu.qwaevisz.tickethandling.ejbservice.facade;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.converter.SystemConverter;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.ComponentStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.SystemStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.persistence.entity.Customer;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Component;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;
import hu.qwaevisz.tickethandling.persistence.service.CompInSystemService;
import hu.qwaevisz.tickethandling.persistence.service.CustomerService;

@PermitAll
@Stateless(mappedName = "ejb/systemFacade")
public class SystemFacadeImpl implements SystemFacade {

	private static final Logger LOGGER = Logger.getLogger(SystemFacadeImpl.class);

	@EJB
	private CustomerService custService;

	@EJB
	private CompInSystemService cisService;

	@EJB
	private SystemConverter sysConverter;

	@Override
	public SystemStub getSystem(String id) throws FacadeException {
		try {
			final SystemStub stub = this.sysConverter.to(this.custService.read(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get System by id (" + id + ") --> " + stub);
			}
			return stub;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<SystemStub> getSystems() throws FacadeException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Systems");
			}
			List<SystemStub> stubs = this.sysConverter.to(this.custService.readAll());
			return stubs;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<String> getSysLabels() throws FacadeException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get System labels");
			}
			List<String> labels = this.custService.readSysLabels();
			return labels;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@RolesAllowed("admin")
	@Override
	public void SaveComponents(SystemStub system) throws FacadeException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Save System Components");
			}

			List<ComponentStub> new_comps = system.getComponents();

			Customer cust = this.custService.read(system.getId());

			this.cisService.remove(cust);

			for (ComponentStub comp : new_comps) {
				this.cisService.create(cust, Component.valueOf(comp.getName()), "");
			}

		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

}
