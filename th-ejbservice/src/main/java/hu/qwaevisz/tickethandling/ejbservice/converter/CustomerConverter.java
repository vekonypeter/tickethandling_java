package hu.qwaevisz.tickethandling.ejbservice.converter;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.ejbservice.domain.CustomerStub;
import hu.qwaevisz.tickethandling.persistence.entity.Customer;

@Local
public interface CustomerConverter {

	CustomerStub to(Customer customer);

	List<CustomerStub> to(List<Customer> customers);

}
