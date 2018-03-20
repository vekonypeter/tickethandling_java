package hu.qwaevisz.tickethandling.ejbservice.converter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;

import hu.qwaevisz.tickethandling.ejbservice.domain.CustomerStub;
import hu.qwaevisz.tickethandling.persistence.entity.Customer;

@PermitAll
@Stateless
public class CustomerConverterImpl implements CustomerConverter {

	@Override
	public CustomerStub to(Customer customer) {
		final String id = customer.getId();
		final String company_name = customer.getName();
		final String address = customer.getAddress();
		final String name = customer.getContact_name();
		final String phone = customer.getContact_phone();
		final String email = customer.getContact_mail();

		return new CustomerStub(id, company_name, address, name, phone, email);
	}

	@Override
	public List<CustomerStub> to(List<Customer> customers) {
		final List<CustomerStub> result = new ArrayList<CustomerStub>();
		for (final Customer customer : customers) {
			result.add(this.to(customer));
		}
		return result;
	}

}
