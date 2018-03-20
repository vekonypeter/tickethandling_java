package hu.qwaevisz.tickethandling.ejbservice.facade;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.EmployeeStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;;

@Local
public interface EmployeeFacade {

	EmployeeStub getEmployee(String id) throws FacadeException;

	List<EmployeeStub> getEmployees() throws FacadeException;

	List<String> getEmpLabels() throws FacadeException;
}
