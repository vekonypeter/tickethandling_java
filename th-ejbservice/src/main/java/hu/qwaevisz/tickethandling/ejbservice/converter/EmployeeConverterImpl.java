package hu.qwaevisz.tickethandling.ejbservice.converter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.EmployeeStub;
import hu.qwaevisz.tickethandling.persistence.entity.Employee;

@PermitAll
@Stateless
public class EmployeeConverterImpl implements EmployeeConverter {

	@EJB
	TicketConverter ticConv;

	@Override
	public EmployeeStub to(Employee emp) {
		return new EmployeeStub(emp.getId(), emp.getName(), emp.getEmail(), emp.getLevel());
	}

	@Override
	public List<EmployeeStub> to(List<Employee> emps) {
		final List<EmployeeStub> result = new ArrayList<EmployeeStub>();
		for (final Employee emp : emps) {
			result.add(this.to(emp));
		}
		return result;
	}

}
