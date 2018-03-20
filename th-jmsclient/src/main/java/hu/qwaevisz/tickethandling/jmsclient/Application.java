package hu.qwaevisz.tickethandling.jmsclient;

public class Application {

	private static final String PROVIDER_URL = "remote://localhost:4447";
	private static final String USERNAME = "jmstestuser";
	private static final String PASSWORD = "User#70365";
	private static final String DESTINATION_TICKETING = "jms/queue/ticketingqueue";

	public static void main(final String[] args) {
		sendSingleMessage();
	}

	public static void sendSingleMessage() {
		try {
			new QueueMessageProducer(Application.PROVIDER_URL, Application.USERNAME, Application.PASSWORD, Application.DESTINATION_TICKETING)
					.standaloneSendMessage("AES-324,Mr Message Bean,MEDIUM,Very big,Nope,Please take care of this");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
