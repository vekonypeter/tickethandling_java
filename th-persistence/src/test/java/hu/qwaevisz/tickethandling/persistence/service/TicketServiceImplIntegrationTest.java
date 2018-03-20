package hu.qwaevisz.tickethandling.persistence.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import hu.qwaevisz.tickethandling.persistence.entity.Customer;
import hu.qwaevisz.tickethandling.persistence.entity.Employee;
import hu.qwaevisz.tickethandling.persistence.entity.Ticket;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Priority;
import hu.qwaevisz.tickethandling.persistence.entity.trunk.Status;
import hu.qwaevisz.tickethandling.persistence.exception.PersistenceServiceException;

public class TicketServiceImplIntegrationTest {

	private static final String PERSISTENCE_UNIT = "th-persistence-test-unit";

	private TicketServiceImpl object;
	private EmployeeServiceImpl object2;
	private CustomerServiceImpl object3;

	@BeforeClass
	private void setUp() {
		Thread.currentThread().setContextClassLoader(new ClassLoader() {
			@Override
			public Enumeration<URL> getResources(String name) throws IOException {
				if (name.equals("META-INF/persistence.xml")) {
					return Collections.enumeration(Arrays.asList(new File("src/test/resources/persistence.xml").toURI().toURL()));
				}
				return super.getResources(name);
			}
		});

		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		final EntityManager entityManager = factory.createEntityManager();

		this.object = new TicketServiceImpl();
		this.object.setEntityManager(entityManager);

		this.object2 = new EmployeeServiceImpl();
		this.object2.setEntityManager(entityManager);

		this.object3 = new CustomerServiceImpl();
		this.object3.setEntityManager(entityManager);
	}

	@Test(groups = "integration")
	private void readSampleTicket() throws PersistenceServiceException {
		final Ticket ticket = this.object.read("AES32420170316051534");
		this.assertTicket(ticket, "AES32420170316051534", Priority.HIGH, Status.IN_PROGRESS, "E4412");
	}

	private void assertTicket(final Ticket ticket, final String id, final Priority priority, final Status status, final String processor) {
		Assert.assertEquals(ticket.getId(), id);
		Assert.assertEquals(ticket.getPriority(), priority);
		Assert.assertEquals(ticket.getStatus(), status);
		Assert.assertEquals(ticket.getProcessor().getId(), processor);
	}

	@Test(groups = "integration")
	private void readSampleEmployee() throws PersistenceServiceException {
		final Employee emp = this.object2.read("E4412");

		Assert.assertEquals(emp.getId(), "E4412");
	}

	@Test(groups = "integration")
	private void readSampleCustomer() throws PersistenceServiceException {
		final Customer cust = this.object3.read("AES-324");

		Assert.assertEquals(cust.getId(), "AES-324");
	}

	@AfterClass
	private void tearDown() {
		this.object.getEntityManager().close();
	}

}
