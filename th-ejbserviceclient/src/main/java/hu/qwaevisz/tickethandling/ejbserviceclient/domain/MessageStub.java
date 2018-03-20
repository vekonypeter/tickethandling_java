package hu.qwaevisz.tickethandling.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class MessageStub implements Serializable, Comparable<MessageStub> {

	private static final long serialVersionUID = -8224247450743936117L;

	private String from;
	private String to;
	private Date date;
	private String text;

	public MessageStub(String from, String to, Date date, String text) {
		this.from = from;
		this.to = to;
		this.date = date;
		this.text = text;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "MessageStub [from=" + this.from + ", to=" + this.to + ", date=" + this.date + ", text=" + this.text + "]";
	}

	@Override
	public int compareTo(MessageStub other) {
		if (this.date.after(other.date)) {
			return -1;
		} else {
			return 1;
		}
	}
}
