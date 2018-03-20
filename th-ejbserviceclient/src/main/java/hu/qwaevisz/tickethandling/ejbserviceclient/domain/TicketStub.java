package hu.qwaevisz.tickethandling.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TicketStub implements Serializable {

	private static final long serialVersionUID = 1232681963319950862L;

	private String id;
	private SystemStub system;
	private String sender_name;
	private PriorityStub priority;
	private String business_impact;
	private String steps_to_rep;
	private Date creationdate;
	private Integer level;
	private EmployeeStub processor;
	private StatusStub status;
	private Date lastchanged;
	private List<MessageStub> conversation;

	public TicketStub() {
		this("", null, "", null, "", "", new Date(), 0, null, null, new Date(), null);
	}

	public TicketStub(String id, SystemStub system, String sender_name, PriorityStub priority, String business_impact, String steps_to_rep, Date creationdate,
			Integer level, EmployeeStub processor, StatusStub status, Date lastchanged, List<MessageStub> conversation) {
		super();
		this.id = id;
		this.system = system;
		this.sender_name = sender_name;
		this.priority = priority;
		this.business_impact = business_impact;
		this.steps_to_rep = steps_to_rep;
		this.creationdate = creationdate;
		this.level = level;
		this.processor = processor;
		this.status = status;
		this.lastchanged = lastchanged;
		this.conversation = conversation;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SystemStub getSystem() {
		return this.system;
	}

	public void setSystem(SystemStub system) {
		this.system = system;
	}

	public String getSender_name() {
		return this.sender_name;
	}

	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

	public PriorityStub getPriority() {
		return this.priority;
	}

	public void setPriority(PriorityStub priority) {
		this.priority = priority;
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

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public EmployeeStub getProcessor() {
		return this.processor;
	}

	public void setProcessor(EmployeeStub processor) {
		this.processor = processor;
	}

	public StatusStub getStatus() {
		return this.status;
	}

	public void setStatus(StatusStub status) {
		this.status = status;
	}

	public Date getLastchanged() {
		return this.lastchanged;
	}

	public void setLastchanged(Date lastchanged) {
		this.lastchanged = lastchanged;
	}

	public List<MessageStub> getConversation() {
		return this.conversation;
	}

	public void setConversation(List<MessageStub> conversation) {
		this.conversation = conversation;
	}

	@Override
	public String toString() {
		return "TicketStub [id=" + this.id + ", system=" + this.system + ", sender_name=" + this.sender_name + ", priority=" + this.priority
				+ ", business_impact=" + this.business_impact + ", steps_to_rep=" + this.steps_to_rep + ", creationdate=" + this.creationdate + ", level="
				+ this.level + ", processor=" + this.processor + ", status=" + this.status + ", lastchanged=" + this.lastchanged + ", conversation="
				+ this.conversation + "]";
	}

}
