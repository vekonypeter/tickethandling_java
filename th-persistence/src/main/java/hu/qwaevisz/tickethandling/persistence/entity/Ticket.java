package hu.qwaevisz.tickethandling.persistence.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hu.qwaevisz.tickethandling.persistence.entity.trunk.Priority;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Status;
import hu.qwaevisz.tickethandling.persistence.parameter.TicketParameter;
import hu.qwaevisz.tickethandling.persistence.query.TicketQuery;

@Entity
@Table(name = "ticket")
@NamedQueries(value = { //
		@NamedQuery(name = TicketQuery.COUNT_BY_ID, query = "SELECT COUNT(t) FROM Ticket t WHERE t.id=:" + TicketParameter.ID),
		@NamedQuery(name = TicketQuery.GET_BY_ID, query = "SELECT t FROM Ticket t LEFT JOIN FETCH t.processor WHERE t.id=:" + TicketParameter.ID),
		@NamedQuery(name = TicketQuery.GET_BY_PROCESSOR, query = "SELECT t FROM Ticket t LEFT JOIN FETCH t.processor p WHERE p.id=:"
				+ TicketParameter.PROCESSOR),
		@NamedQuery(name = TicketQuery.GET_BY_LEVEL_AND_PROCESSOR, query = "SELECT t FROM Ticket t LEFT JOIN FETCH t.processor p WHERE p.id=:"
				+ TicketParameter.PROCESSOR + " AND t.level=:" + TicketParameter.LEVEL),
		@NamedQuery(name = TicketQuery.GET_ALL, query = "SELECT t FROM Ticket t LEFT JOIN FETCH t.processor p  ORDER BY t.id"),
		@NamedQuery(name = TicketQuery.REMOVE_BY_ID, query = "DELETE FROM Ticket t WHERE t.id=:" + TicketParameter.ID)
		//
})
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1525352421414297015L;

	@Id
	@Column(name = "tic_id", nullable = false)
	private String id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "tic_sys_id", referencedColumnName = "cust_sys_id", nullable = false)
	private Customer system;

	@Column(name = "tic_sender_name", nullable = false)
	private String sender_name;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tic_priority", nullable = false)
	private Priority priority;

	@Column(name = "tic_business_imp", nullable = false)
	private String business_impact;

	@Column(name = "tic_steps_to_rep", nullable = false)
	private String steps_to_rep;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tic_creationdate", nullable = false)
	private Date creationdate;

	@Column(name = "tic_level", nullable = false)
	private Integer level;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tic_processor_id", referencedColumnName = "emp_id", nullable = false)
	private Employee processor;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tic_status", nullable = false)
	private Status status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tic_lastchanged", nullable = false)
	private Date lastchanged;

	public static DateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);

	public Ticket() throws ParseException {
		this("", null, "", Priority.LOW, "", "", new Date(), 1, null, Status.NEW, new Date());
	}

	public Ticket(String id, Customer system, String sender_name, Priority priority, String business_impact, String steps_to_rep, Date creationdate,
			Integer level, Employee processor, Status status, Date lastchanged) {
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
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer getSystem() {
		return this.system;
	}

	public void setSystem(Customer system) {
		this.system = system;
	}

	public String getSender_name() {
		return this.sender_name;
	}

	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(Priority priority) {
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

	public Employee getProcessor() {
		return this.processor;
	}

	public void setProcessor(Employee processor) {
		this.processor = processor;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getLastchanged() {
		return this.lastchanged;
	}

	public void setLastchanged(Date lastchanged) {
		this.lastchanged = lastchanged;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + this.id + ", system=" + this.system + ", sender_name=" + this.sender_name + ", priority=" + this.priority + ", business_impact="
				+ this.business_impact + ", steps_to_rep=" + this.steps_to_rep + ", creationdate=" + this.creationdate + ", level=" + this.level
				+ ", processor=" + this.processor + ", status=" + this.status + ", lastchanged=" + this.lastchanged + "]";
	}
}
