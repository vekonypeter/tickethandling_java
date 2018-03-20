package hu.qwaevisz.tickethandling.persistence.service;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.persistence.entity.Employee;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;

@Local
public interface EmployeeService {

	boolean exists(String id) throws PersistenceServiceException;

	Employee read(String id) throws PersistenceServiceException;

	List<String> readEmpLabels() throws PersistenceServiceException;

	List<Employee> readAll() throws PersistenceServiceException;
}
