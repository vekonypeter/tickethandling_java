package hu.qwaevisz.tickethandling.persistence.entity;

import java.util.Date;

public class Message {

	String id;
	String from;
	String to;
	Date date;
	String text;

	public Message(String id, String from, String to, Date date, String text) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.date = date;
		this.text = text;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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
		return "Message [id=" + this.id + ", from=" + this.from + ", to=" + this.to + ", date=" + this.date + ", text=" + this.text + "]";
	}

}
