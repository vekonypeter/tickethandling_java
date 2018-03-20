
package hu.qwaevisz.tickethandling.ejbservice.facade;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.converter.CustomerConverter;
import hu.qwaevisz.tickethandling.ejbservice.domain.CustomerStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.persistence.entity.Customer;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;
import hu.qwaevisz.tickethandling.persistence.service.CustomerService;

@RolesAllowed("admin")
@Stateless(mappedName = "ejb/customerFacade")
public class CustomerFacadeImpl implements CustomerFacade {

	private static final Logger LOGGER = Logger.getLogger(CustomerFacadeImpl.class);

	@EJB
	private CustomerService custService;

	@EJB
	private CustomerConverter custConv;

	@Override
	public CustomerStub getCustomer(String systemId) throws FacadeException {
		try {
			final CustomerStub stub = this.custConv.to(this.custService.read(systemId));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Customer by system id (" + systemId + ") --> " + stub);
			}
			return stub;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<CustomerStub> getCustomers() throws FacadeException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Customers");
			}
			List<CustomerStub> stubs = this.custConv.to(this.custService.readAll());
			return stubs;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	@Override
	public CustomerStub createCustomer(String systemId, String name, String address, String contact_name, String contact_phone, String contact_mail)
			throws FacadeException {
		try {
			Customer customer = this.custService.create(systemId, name, address, contact_name, contact_phone, contact_mail);
			return this.custConv.to(customer);

		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}

	}

	@Override
	public void remove(String systemId) throws FacadeException {
		try {
			this.custService.delete(systemId);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

}
