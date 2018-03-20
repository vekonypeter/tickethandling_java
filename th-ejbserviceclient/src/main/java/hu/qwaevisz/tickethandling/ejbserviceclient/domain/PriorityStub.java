package hu.qwaevisz.tickethandling.ejbserviceclient.domain;

public enum PriorityStub {

	VERY_LOW("Very Low"),
	LOW("Low"),
	NORMAL("Normal"),
	MEDIUM("Medium"),
	HIGH("High"),
	IMMEDIATE("Immediate");

	private final String label;

	private PriorityStub(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name();
	}

}
