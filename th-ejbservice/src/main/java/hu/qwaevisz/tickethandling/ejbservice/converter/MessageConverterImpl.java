package hu.qwaevisz.tickethandling.ejbservice.converter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.MessageStub;
import hu.qwaevisz.tickethandling.persistence.entity.Message;

@PermitAll
@Stateless
public class MessageConverterImpl implements MessageConverter {

	@Override
	public MessageStub to(Message msg) {
		return new MessageStub(msg.getFrom(), msg.getTo(), msg.getDate(), msg.getText());
	}

	@Override
	public List<MessageStub> to(List<Message> msgs) {
		final List<MessageStub> result = new ArrayList<MessageStub>();
		for (final Message msg : msgs) {
			result.add(this.to(msg));
		}
		return result;
	}

	@Override
	public Message from(MessageStub msg, String id) {
		return new Message(id, msg.getFrom(), msg.getTo(), msg.getDate(), msg.getText());
	}

	@Override
	public List<Message> from(List<MessageStub> msgs, String id) {
		final List<Message> result = new ArrayList<Message>();
		for (final MessageStub msg : msgs) {
			result.add(this.from(msg, id + msgs.indexOf(msg)));
		}
		return result;
	}

}
