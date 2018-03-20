package hu.qwaevisz.tickethandling.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.List;

public class SystemStub implements Serializable {

	private static final long serialVersionUID = -9042402657546368728L;

	private String id;
	private String company_name;
	private List<ComponentStub> components;

	public SystemStub() {
		this("", "", null);
	}

	public SystemStub(String id, String company_name, List<ComponentStub> components) {
		super();
		this.id = id;
		this.company_name = company_name;
		this.components = components;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompany_name() {
		return this.company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public List<ComponentStub> getComponents() {
		return this.components;
	}

	public void setComponents(List<ComponentStub> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		return "SystemStub [id=" + this.id + ", components=" + this.components + "]";
	}
}
