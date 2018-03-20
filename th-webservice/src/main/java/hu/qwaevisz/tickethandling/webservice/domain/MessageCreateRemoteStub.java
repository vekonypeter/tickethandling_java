package hu.qwaevisz.tickethandling.webservice.domain;

import java.io.Serializable;

public class MessageCreateRemoteStub implements Serializable {

	private static final long serialVersionUID = 3164987237490135272L;

	private String ticketId;
	private String message;

	public MessageCreateRemoteStub() {
		this("", "");
	}

	public MessageCreateRemoteStub(String ticketId, String message) {
		super();
		this.ticketId = ticketId;
		this.message = message;
	}

	public String getTicketId() {
		return this.ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MessageCreateRemoteStub [ticketId=" + this.ticketId + ", message=" + this.message + "]";
	}

}
