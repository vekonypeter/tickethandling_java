package hu.qwaevisz.tickethandling.ejbservice.facade;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.SystemStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;

@Local
public interface SystemFacade {

	SystemStub getSystem(String id) throws FacadeException;

	List<SystemStub> getSystems() throws FacadeException;

	List<String> getSysLabels() throws FacadeException;

	void SaveComponents(SystemStub system) throws FacadeException;
}
