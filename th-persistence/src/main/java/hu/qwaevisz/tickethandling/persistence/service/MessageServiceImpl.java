package hu.qwaevisz.tickethandling.persistence.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import hu.qwaevisz.tickethandling.persistence.entity.Message;

@Stateless(mappedName = "ejb/messageService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MessageServiceImpl implements MessageService {

	private static final Logger LOGGER = Logger.getLogger(MessageServiceImpl.class);

	private static final String XMLFILESPATH = "C:\\TicketingMessages";
	private static final String MESSAGEDATEFORMAT = "yyyy-mm-dd HH:MM:ss";
	private static final String INITIALMESSAGE = "<conversation id=\"%1s\"><message id=\"%2s-0001\"><from>System</from><to>Customer</to><date>%3s</date><text>%4s</text></message></conversation>";

	@Override
	public List<Message> readConversation(String ticketId) throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException {

		String filepath = XMLFILESPATH + "\\" + ticketId + ".xml";
		LOGGER.info("Read conversation: " + filepath);
		File xmlFile = new File(filepath);

		// This is just to ease the work on two notebooks because of the different paths
		if (!xmlFile.exists()) {
			this.createConversation(ticketId);
		}
		//

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);

		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("message");

		ArrayList<Message> conversation = new ArrayList<Message>();

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				String id = eElement.getAttribute("id");
				String from = eElement.getElementsByTagName("from").item(0).getTextContent();
				String to = eElement.getElementsByTagName("to").item(0).getTextContent();
				DateFormat format = new SimpleDateFormat(MESSAGEDATEFORMAT);
				Date date = format.parse(eElement.getElementsByTagName("date").item(0).getTextContent());
				String text = eElement.getElementsByTagName("text").item(0).getTextContent();

				conversation.add(new Message(id, from, to, date, text));
			}
		}

		return conversation;
	}

	@Override
	public List<Message> createConversation(String ticketId) throws FileNotFoundException, IOException {
		return this.createConversation(ticketId, "Initial message");

	}

	@Override
	public List<Message> createConversation(String ticketId, String initialMessage) throws FileNotFoundException, IOException {

		LOGGER.info("Creating conversation XML for Ticket(" + ticketId + ") ...");

		List<Message> conversation = new ArrayList<Message>();

		String filepath = XMLFILESPATH + "\\" + ticketId + ".xml";

		File file = new File(filepath);

		if (file.createNewFile()) {

			LOGGER.info("Conversation XML for Ticket(" + ticketId + ") created!");

			DateFormat format = new SimpleDateFormat(MESSAGEDATEFORMAT);
			Date now = new Date();
			PrintWriter out = new PrintWriter(filepath);
			out.write(String.format(INITIALMESSAGE, ticketId, ticketId, format.format(now), initialMessage));
			out.close();

			conversation.add(new Message(ticketId + "0001", "System", "Customer", now, initialMessage));

		} else {
			throw new PersistenceException("File already Exists!");
		}
		return conversation;
	}

	@Override
	public void saveConversation(List<Message> conversation, String ticketId)
			throws FileNotFoundException, IOException, ParserConfigurationException, SAXException, TransformerException {

		LOGGER.info("Saving conversation XML for Ticket(" + ticketId + ") ...");

		String filepath = XMLFILESPATH + "\\" + ticketId + ".xml";

		DateFormat format = new SimpleDateFormat(MESSAGEDATEFORMAT);

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("conversation");
		doc.appendChild(rootElement);

		Attr attr = doc.createAttribute("ticket");
		attr.setValue(ticketId);
		rootElement.setAttributeNode(attr);

		for (Message message : conversation) {

			Element messageElement = doc.createElement("message");
			Attr messageIdAttr = doc.createAttribute("id");
			messageIdAttr.setValue(message.getId());
			messageElement.setAttributeNode(messageIdAttr);
			rootElement.appendChild(messageElement);

			Element fromElement = doc.createElement("from");
			Node fromNode = doc.createTextNode(message.getFrom());
			fromElement.appendChild(fromNode);
			Element toElement = doc.createElement("to");
			Node toNode = doc.createTextNode(message.getTo());
			toElement.appendChild(toNode);
			Element dateElement = doc.createElement("date");
			Node dateNode = doc.createTextNode(format.format(message.getDate()));
			dateElement.appendChild(dateNode);
			Element textElement = doc.createElement("text");
			Node textNode = doc.createTextNode(message.getText());
			textElement.appendChild(textNode);

			messageElement.appendChild(fromElement);
			messageElement.appendChild(toElement);
			messageElement.appendChild(dateElement);
			messageElement.appendChild(textElement);

		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		File file = new File(filepath);
		FileOutputStream out = new FileOutputStream(file);

		transformer.transform(source, new StreamResult(out));
	}

}
