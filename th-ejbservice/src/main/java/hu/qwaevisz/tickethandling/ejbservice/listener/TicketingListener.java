package hu.qwaevisz.tickethandling.ejbservice.listener;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.facade.TicketFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;

@PermitAll
@MessageDriven(name = "TicketingListener", activationConfig = { //
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "ticketingqueue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class TicketingListener implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(TicketingListener.class);

	@EJB
	private TicketFacade ticFac;

	@PostConstruct
	public void initialize() {
		LOGGER.info("Ticketing Listener created...");
	}

	@Override
	public void onMessage(final Message message) {
		try {
			if (LOGGER.isDebugEnabled()) {
				final Queue destination = (Queue) message.getJMSDestination();
				final String queueName = destination.getQueueName();
				LOGGER.debug("New JMS message arrived into " + queueName + " queue (correlation id: " + message.getJMSCorrelationID() + ")");
			}

			if (message instanceof TextMessage) {
				// final TextMessage textMessage = (TextMessage) message;

				String content = "AES-324,Mr Message Bean,MEDIUM,Very big,Nope,Please take care of this";
				;// textMessage.getText();
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Received message: " + content);
				}
				content = content.replace("[", "");
				content = content.replace("]", "");
				final String[] msgData = content.split(",");

				LOGGER.info(msgData[0] + msgData[1] + PriorityStub.valueOf(msgData[2]) + msgData[3] + msgData[4] + msgData[5]);

				PriorityStub prio = PriorityStub.valueOf(msgData[2]);

				LOGGER.info(prio);

				this.ticFac.createTicket(msgData[0], msgData[1], prio, msgData[3], msgData[4], msgData[5]);

			} else {
				LOGGER.error("Received message is not a TextMessage (" + message + ")");
			}
		} catch (final JMSException | FacadeException | NumberFormatException e) {
			LOGGER.error(e, e);
		}
	}

}
