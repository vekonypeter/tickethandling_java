package hu.qwaevisz.tickethandling.ejbservice.domain;

import java.io.Serializable;

public class CustomerStub implements Serializable {

	private static final long serialVersionUID = -5361122404558144789L;

	private String id;
	private String name;
	private String address;
	private String contact_name;
	private String contact_phone;
	private String contact_mail;

	public CustomerStub() {
		this("", "", "", "", "", "");
	}

	public CustomerStub(String id, String name, String address, String contact_name, String contact_phone, String contact_mail) {
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
		return "CustomerStub [id=" + this.id + ", name=" + this.name + ", address=" + this.address + ", contact_name=" + this.contact_name + ", contact_phone="
				+ this.contact_phone + ", contact_mail=" + this.contact_mail + "]";
	}

}
