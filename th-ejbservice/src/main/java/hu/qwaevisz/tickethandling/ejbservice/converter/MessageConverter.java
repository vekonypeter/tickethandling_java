package hu.qwaevisz.tickethandling.ejbservice.converter;

import java.util.List;

import javax.ejb.Local;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.MessageStub;
import hu.qwaevisz.tickethandling.persistence.entity.Message;

@Local
public interface MessageConverter {

	MessageStub to(Message msg);

	List<MessageStub> to(List<Message> msgs);

	Message from(MessageStub msg, String id);

	List<Message> from(List<MessageStub> msgs, String id);

}
