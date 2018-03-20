package hu.qwaevisz.tickethandling.ejbservice.converter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.ComponentStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.SystemStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.persistence.entity.CompInSystem;
import hu.qwaevisz.tickethandling.persistence.entity.Customer;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;
import hu.qwaevisz.tickethandling.persistence.service.CompInSystemService;

@PermitAll
@Stateless
public class SystemConverterImpl implements SystemConverter {

	@EJB
	private CompInSystemService cisService;

	@Override
	public SystemStub to(Customer customer) throws FacadeException {
		final String id = customer.getId();
		final String company_name = customer.getName();

		final List<ComponentStub> components = new ArrayList<ComponentStub>();
		List<CompInSystem> compinsyss;
		try {
			compinsyss = this.cisService.getByCustomer(customer);
		} catch (PersistenceServiceException e) {
			throw new FacadeException(e.getLocalizedMessage());
		}

		for (final CompInSystem compinsys : compinsyss) {
			components.add(ComponentStub.valueOf(compinsys.getComponent().toString()));
		}

		return new SystemStub(id, company_name, components);
	}

	@Override
	public List<SystemStub> to(List<Customer> customers) throws FacadeException {
		final List<SystemStub> result = new ArrayList<SystemStub>();
		for (final Customer customer : customers) {
			result.add(this.to(customer));
		}
		return result;
	}

}
