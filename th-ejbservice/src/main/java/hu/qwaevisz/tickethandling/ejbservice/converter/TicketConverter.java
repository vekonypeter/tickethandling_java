package hu.qwaevisz.tickethandling.ejbservice.converter;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.ejb.Local;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.persistence.entity.Ticket;

@Local
public interface TicketConverter {

	TicketStub to(Ticket ticket) throws DOMException, ParserConfigurationException, SAXException, IOException, ParseException, FacadeException;

	List<TicketStub> to(List<Ticket> tickets) throws DOMException, ParserConfigurationException, SAXException, IOException, ParseException, FacadeException;

}
