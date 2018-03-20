package hu.qwaevisz.tickethandling.ejbserviceclient.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 7600795664768656658L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
