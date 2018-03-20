package hu.qwaevisz.tickethandling.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hu.qwaevisz.tickethandling.persistence.parameter.EmployeeParameter;
import hu.qwaevisz.tickethandling.persistence.query.EmployeeQuery;

@Entity
@Table(name = "employee")
@NamedQueries(value = { //
		@NamedQuery(name = EmployeeQuery.COUNT_BY_ID, query = "SELECT COUNT(e) FROM Employee e WHERE e.id=:" + EmployeeParameter.ID),
		@NamedQuery(name = EmployeeQuery.GET_BY_ID, query = "SELECT e FROM Employee e WHERE e.id=:" + EmployeeParameter.ID),
		@NamedQuery(name = EmployeeQuery.GET_ALL, query = "SELECT e FROM Employee e ORDER BY e.id"),
		@NamedQuery(name = EmployeeQuery.GET_EMPLABELS, query = "SELECT e.id FROM Employee e ORDER BY e.id"),
		@NamedQuery(name = EmployeeQuery.REMOVE_BY_ID, query = "DELETE FROM Employee e WHERE e.id=:" + EmployeeParameter.ID)
		//
})
public class Employee implements Serializable {

	private static final long serialVersionUID = 1525352421414297015L;

	@Id
	@Column(name = "emp_id", nullable = false)
	private String id;

	@Column(name = "emp_name", nullable = false)
	private String name;

	@Column(name = "emp_phone", nullable = false)
	private String phone;

	@Column(name = "emp_email", nullable = false)
	private String email;

	@Column(name = "emp_level", nullable = false)
	private Integer level;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "emp_hiredate", nullable = false)
	private Date hiredate;

	public Employee(String id, String name, String phone, String email, Integer level, Date hiredate) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.level = level;
		this.hiredate = hiredate;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Employee() {
		this("", "", "", "", 0, new Date());
	}

	@Override
	public String toString() {
		return "Employee [id=" + this.id + ", name=" + this.name + ", phone=" + this.phone + ", email=" + this.email + ", level=" + this.level + ", hiredate="
				+ this.hiredate + "]";
	}
}
