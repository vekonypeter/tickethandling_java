package hu.qwaevisz.tickethandling.persistence.service;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.persistence.entity.CompInSystem;
import hu.qwaevisz.tickethandling.persistence.entity.Customer;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Component;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;

@Local
public interface CompInSystemService {

	List<CompInSystem> getByCustomer(Customer customer) throws PersistenceServiceException;

	CompInSystem create(Customer customer, Component component, String description) throws PersistenceServiceException;

	void remove(Customer customer, Component component) throws PersistenceServiceException;

	void remove(Customer customer) throws PersistenceServiceException;
}
