package hu.qwaevisz.tickethandling.webservice.domain;

import java.io.Serializable;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub;

public class TicketCreateRemoteStub implements Serializable {

	private static final long serialVersionUID = 4562127909486614840L;

	private String systemId;
	private PriorityStub priority;
	private String sender_name;
	private String business_impact;
	private String steps_to_rep;
	private String initialMessage;

	public TicketCreateRemoteStub() {
		this("", PriorityStub.NORMAL, "", "", "", "");
	}

	public TicketCreateRemoteStub(String systemId, PriorityStub priority, String sender_name, String business_impact, String steps_to_rep,
			String initialMessage) {
		super();
		this.systemId = systemId;
		this.priority = priority;
		this.sender_name = sender_name;
		this.business_impact = business_impact;
		this.steps_to_rep = steps_to_rep;
		this.initialMessage = initialMessage;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public PriorityStub getPriority() {
		return this.priority;
	}

	public void setPriority(PriorityStub priority) {
		this.priority = priority;
	}

	public String getSender_name() {
		return this.sender_name;
	}

	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

	public String getBusiness_impact() {
		return this.business_impact;
	}

	public void setBusiness_impact(String business_impact) {
		this.business_impact = business_impact;
	}

	public String getSteps_to_rep() {
		return this.steps_to_rep;
	}

	public void setSteps_to_rep(String steps_to_rep) {
		this.steps_to_rep = steps_to_rep;
	}

	public String getInitialMessage() {
		return this.initialMessage;
	}

	public void setInitialMessage(String initialMessage) {
		this.initialMessage = initialMessage;
	}

	@Override
	public String toString() {
		return "TicketCreateRemoteStub [systemId=" + this.systemId + ", priority=" + this.priority + ", sender_name=" + this.sender_name + ", business_impact="
				+ this.business_impact + ", steps_to_rep=" + this.steps_to_rep + ", messageText=" + this.initialMessage + "]";
	}

}
