package hu.qwaevisz.tickethandling.ejbservice.facade;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.ejbservice.domain.CustomerStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;

@Local
public interface CustomerFacade {

	CustomerStub getCustomer(String systemId) throws FacadeException;

	List<CustomerStub> getCustomers() throws FacadeException;

	CustomerStub createCustomer(String systemId, String name, String address, String contact_name, String contact_phone, String contact_mail)
			throws FacadeException;

	void remove(String systemId) throws FacadeException;
}
