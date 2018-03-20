package hu.qwaevisz.tickethandling.ejbserviceclient.domain;

public enum ComponentStub {

	XX4CASD0("XX-4C-ASD0"),
	YY66WERT("YY-66-WERT"),
	GZ52TZUU("GZ-52-TZUU"),
	AF21HJKG("AF-21-HJKG"),
	GR44LLKJ("GR-44-LLKJ"),
	TV8GMNBV("TV-8G-MNBV");

	private final String label;

	private ComponentStub(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name();
	}
}
