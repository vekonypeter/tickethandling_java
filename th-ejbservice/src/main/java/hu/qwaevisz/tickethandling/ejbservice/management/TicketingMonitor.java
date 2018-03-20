package hu.qwaevisz.tickethandling.ejbservice.management;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.domain.TicketCriteria;
import hu.qwaevisz.tickethandling.ejbservice.facade.TicketFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;

public class TicketingMonitor implements TicketingMonitorMBean {

	private static final Logger LOGGER = Logger.getLogger(TicketingMonitor.class);

	@EJB
	TicketFacade ticFac;

	public void start() throws Exception {
		LOGGER.info("Start Ticketing MBean");
	}

	public void stop() throws Exception {
		LOGGER.info("Stop Ticketing MBean");
	}

	@Override
	public int countEmpTickets(String employeeId) {
		try {
			return this.ticFac.getTicketsByProcessor(employeeId).size();
		} catch (ServiceException e) {
			LOGGER.error(e, e);
			return -1;
		} catch (Exception e) {
			LOGGER.error(e, e);
			return -1;
		}
	}

	@Override
	public int countImmediateTickets() {
		try {
			TicketCriteria crit = new TicketCriteria();
			crit.setPriority(PriorityStub.IMMEDIATE);

			return this.ticFac.getTickets(crit).size();

		} catch (FacadeException e) {
			LOGGER.error(e, e);
			return -1;
		}
	}

	@Override
	public String sendNewTicket(String systemId, String sender_name, String priority, String business_impact, String steps_to_rep, String initial_message) {
		try {

			this.ticFac.createTicket(systemId, sender_name, PriorityStub.valueOf(priority), business_impact, steps_to_rep, initial_message);

			return "OK";

		} catch (FacadeException e) {
			LOGGER.error(e, e);
			return "NOPE";
		}
	}

}
