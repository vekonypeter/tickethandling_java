package hu.qwaevisz.tickethandling.ejbservice.domain;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.StatusStub;

public class TicketCriteria {

	private String id;
	private String processorId;
	private String system;
	private PriorityStub priority;
	private StatusStub status;
	private Integer level;

	public TicketCriteria() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystem() {
		return this.system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public PriorityStub getPriority() {
		return this.priority;
	}

	public void setPriority(PriorityStub priority) {
		this.priority = priority;
	}

	public StatusStub getStatus() {
		return this.status;
	}

	public void setStatus(StatusStub status) {
		this.status = status;
	}

	public String getProcessorId() {
		return this.processorId;
	}

	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "TicketCriteria [id=" + this.id + ", processorId=" + this.processorId + ", system=" + this.system + ", priority=" + this.priority + ", status="
				+ this.status + ", level=" + this.level + "]";
	}

}
