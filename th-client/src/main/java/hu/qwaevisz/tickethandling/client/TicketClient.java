package hu.qwaevisz.tickethandling.client;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbserviceclient.TicketFacadeRemote;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.MessageStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.ServiceException;

public class TicketClient {

	private static final String JBOSS_INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
	private static final String JBOSS_PROVIDER_URL = "remote://localhost:4447";
	private static final String JBOSS_URL_PKG_PREFIXES = "org.jboss.ejb.client.naming";

	private static final String JBOSS_NAMING_CLIENT_EJB_CONTEXT_KEY = "jboss.naming.client.ejb.context";
	private static final String JBOSS_NAMING_CLIENT_EJB_CONTEXT_VALUE = "true";

	private static final Logger LOGGER = Logger.getLogger(TicketClient.class);

	public static void main(final String[] args) throws Exception {
		System.out.println("REMOTE EJB CLIENT");
		System.out.println();
		TicketStub ticket = new TicketClient().invoke("RTS75820170416051534");

		if (ticket != null) {
			System.out.println("TICKET " + ticket.getId());
			System.out.println("+ Priority:\t\t" + ticket.getPriority().getLabel());
			System.out.println("+ Status:\t\t" + ticket.getStatus().getLabel());
			System.out.println("+ System:\t\t" + ticket.getSystem().getId() + "/" + ticket.getSystem().getCompany_name());
			System.out.println("+ Level:\t\t" + ticket.getLevel());
			System.out.println("+ Processor:\t\t" + ticket.getProcessor().getId() + "/" + ticket.getProcessor().getName());
			System.out.println("+ Business impact:\t" + ticket.getBusiness_impact());
			System.out.println("+ Steps to reproduce:\t" + ticket.getSteps_to_rep());
			System.out.println("+ Sender:\t\t" + ticket.getSender_name());
			System.out.println("+ Creation date:\t" + ticket.getCreationdate());
			System.out.println("+ Last changed:\t\t" + ticket.getLastchanged());
			System.out.println("+ Messages:");
			List<MessageStub> conv = ticket.getConversation();
			for (int i = 0; i < conv.size(); i++) {
				System.out.println((i + 1) + ".) =============");
				System.out.println("from: " + conv.get(i).getFrom());
				System.out.println("to: " + conv.get(i).getTo());
				System.out.println("date: " + conv.get(i).getDate());
				System.out.println("text: " + conv.get(i).getText());
			}
		}
	}

	private TicketStub invoke(final String id) {
		TicketStub ticket = null;
		try {
			final TicketFacadeRemote facade = this.lookup();
			ticket = facade.getTicket(id);

			System.out.println("GOT THE TICKET");

		} catch (final ServiceException e) {
			e.printStackTrace();
		} catch (final NamingException e) {
			e.printStackTrace();
		}
		return ticket;
	}

	private TicketFacadeRemote lookup() throws NamingException {
		final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, JBOSS_INITIAL_CONTEXT_FACTORY);
		jndiProperties.put(Context.PROVIDER_URL, JBOSS_PROVIDER_URL);
		jndiProperties.put(Context.URL_PKG_PREFIXES, JBOSS_URL_PKG_PREFIXES);
		jndiProperties.put(JBOSS_NAMING_CLIENT_EJB_CONTEXT_KEY, JBOSS_NAMING_CLIENT_EJB_CONTEXT_VALUE);
		final Context context = new InitialContext(jndiProperties);
		return (TicketFacadeRemote) context
				.lookup("tickethandling/th-ejbservice/TicketFacadeImpl!hu.qwaevisz.tickethandling.ejbserviceclient.TicketFacadeRemote");
	}
}