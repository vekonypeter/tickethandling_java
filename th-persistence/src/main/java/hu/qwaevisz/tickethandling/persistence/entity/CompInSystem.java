package hu.qwaevisz.tickethandling.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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

import hu.qwaevisz.tickethandling.persistence.entity.trunk.Component;
import hu.qwaevisz.tickethandling.persistence.parameter.CompInSystemParameter;
import hu.qwaevisz.tickethandling.persistence.query.CompInSystemQuery;

@Entity
@Table(name = "comp_in_system")
@NamedQueries(value = { //
		@NamedQuery(name = CompInSystemQuery.GET_BY_SYS, query = "SELECT cis FROM CompInSystem cis WHERE cis.system=:" + CompInSystemParameter.CUSTOMER),
		@NamedQuery(name = CompInSystemQuery.GET_BY_COMP, query = "SELECT cis FROM CompInSystem cis WHERE cis.component=:" + CompInSystemParameter.COMPONENT),
		@NamedQuery(name = CompInSystemQuery.GET_BY_SYS_COMP, query = "SELECT cis FROM CompInSystem cis WHERE cis.component=:" + CompInSystemParameter.COMPONENT
				+ " AND cis.system=:" + CompInSystemParameter.CUSTOMER),
		@NamedQuery(name = CompInSystemQuery.GET_ALL, query = "SELECT c FROM CompInSystem c"),
		@NamedQuery(name = CompInSystemQuery.REMOVE_BY_COMP, query = "DELETE FROM CompInSystem c WHERE c.component=:" + CompInSystemParameter.COMPONENT),
		@NamedQuery(name = CompInSystemQuery.REMOVE_BY_SYS, query = "DELETE FROM CompInSystem c WHERE c.system=:" + CompInSystemParameter.CUSTOMER),
		@NamedQuery(name = CompInSystemQuery.REMOVE_BY_SYS_COMP, query = "DELETE FROM CompInSystem c WHERE c.system=:" + CompInSystemParameter.CUSTOMER
				+ " AND c.component=:" + CompInSystemParameter.COMPONENT)
		//
})
public class CompInSystem implements Serializable {

	private static final long serialVersionUID = 1525352421414297015L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "cis_sys_id", referencedColumnName = "cust_sys_id", nullable = false)
	private Customer system;

	@Id
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "cis_comp_id", nullable = false)
	private Component component;

	@Column(name = "cis_description", nullable = false)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cis_creationdate", nullable = false)
	private Date creationdate;

	public CompInSystem() {
		this(null, null, "", new Date());
	}

	public CompInSystem(Customer system, Component component, String description, Date creationdate) {
		super();
		this.system = system;
		this.component = component;
		this.description = description;
		this.creationdate = creationdate;
	}

	public Customer getSystem() {
		return this.system;
	}

	public void setSystem(Customer system) {
		this.system = system;
	}

	public Component getComponent() {
		return this.component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	@Override
	public String toString() {
		return "CompInSystem [system=" + this.system + ", component=" + this.component + ", description=" + this.description + ", creationdate="
				+ this.creationdate + "]";
	}
}
