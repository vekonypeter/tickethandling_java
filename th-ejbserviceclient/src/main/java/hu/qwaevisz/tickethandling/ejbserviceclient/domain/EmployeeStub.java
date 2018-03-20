package hu.qwaevisz.tickethandling.ejbserviceclient.domain;

import java.io.Serializable;

public class EmployeeStub implements Serializable {

	private static final long serialVersionUID = 7800019955248688362L;

	private String id;
	private String name;
	private String email;
	private Integer level;

	public EmployeeStub() {
		this("", "", "", 0);
	}

	public EmployeeStub(String id, String name, String email, Integer level) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.level = level;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "EmployeeStub [id=" + this.id + ", name=" + this.name + ", email=" + this.email + ", level=" + this.level + "]";
	}

}
