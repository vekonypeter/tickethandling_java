package hu.qwaevisz.tickethandling.ejbserviceclient.domain;

public enum StatusStub {

	NEW("New"),
	IN_PROGRESS("In Progess"),
	SOLUTION_PROVIDED("Solution Provided"),
	SOLVED("Solved"),
	CLOSED("Closed");

	private final String label;

	private StatusStub(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name();
	}

}
