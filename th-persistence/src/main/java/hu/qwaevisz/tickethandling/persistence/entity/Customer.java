package hu.qwaevisz.tickethandling.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import hu.qwaevisz.tickethandling.persistence.parameter.CustomerParameter;
import hu.qwaevisz.tickethandling.persistence.query.CustomerQuery;

@Entity
@Table(name = "customer")
@NamedQueries(value = { //
		@NamedQuery(name = CustomerQuery.COUNT_BY_ID, query = "SELECT COUNT(c) FROM Customer c WHERE c.id=:" + CustomerParameter.ID),
		@NamedQuery(name = CustomerQuery.GET_BY_ID, query = "SELECT c FROM Customer c WHERE c.id=:" + CustomerParameter.ID),
		@NamedQuery(name = CustomerQuery.GET_ALL, query = "SELECT c FROM Customer c ORDER BY c.id"),
		@NamedQuery(name = CustomerQuery.GET_SYSLABELS, query = "SELECT c.id FROM Customer c ORDER BY c.id"),
		@NamedQuery(name = CustomerQuery.REMOVE_BY_ID, query = "DELETE FROM Customer c WHERE c.id=:" + CustomerParameter.ID)

		//
})
public class Customer implements Serializable {

	private static final long serialVersionUID = 1525352421414297015L;

	@Id
	@Column(name = "cust_sys_id", nullable = false)
	private String id;

	@Column(name = "cust_name", nullable = false)
	private String name;

	@Column(name = "cust_address", nullable = false)
	private String address;

	@Column(name = "cust_cont_name", nullable = false)
	private String contact_name;

	@Column(name = "cust_cont_phone", nullable = false)
	private String contact_phone;

	@Column(name = "cust_cont_mail", nullable = false)
	private String contact_mail;

	public Customer() {
		this("", "", "", "", "", "");
	}

	public Customer(String id, String name, String address, String contact_name, String contact_phone, String contact_mail) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.contact_name = contact_name;
		this.contact_phone = contact_phone;
		this.contact_mail = contact_mail;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact_name() {
		return this.contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_phone() {
		return this.contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getContact_mail() {
		return this.contact_mail;
	}

	public void setContact_mail(String contact_mail) {
		this.contact_mail = contact_mail;
	}

	@Override
	public String toString() {
		return "Customer [id=" + this.id + ", name=" + this.name + ", address=" + this.address + ", contact_name=" + this.contact_name + ", contact_phone="
				+ this.contact_phone + ", contact_mail=" + this.contact_mail + "]";
	}

}
