package hu.qwaevisz.tickethandling.ejbservice.converter;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.EmployeeStub;
import hu.qwaevisz.tickethandling.persistence.entity.Employee;

@Local
public interface EmployeeConverter {

	EmployeeStub to(Employee emp);

	List<EmployeeStub> to(List<Employee> emps);

}
