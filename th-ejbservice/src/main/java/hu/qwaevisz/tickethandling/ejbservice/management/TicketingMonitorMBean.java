package hu.qwaevisz.tickethandling.ejbservice.management;

public interface TicketingMonitorMBean {

	int countEmpTickets(String employeeId);

	int countImmediateTickets();

	String sendNewTicket(String systemId, String sender_name, String priority, String business_impact, String steps_to_rep, String initial_message);

}
