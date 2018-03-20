package hu.qwaevisz.tickethandling.weblayer.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import hu.qwaevisz.tickethandling.ejbservice.domain.TicketCriteria;
import hu.qwaevisz.tickethandling.ejbservice.facade.EmployeeFacade;
import hu.qwaevisz.tickethandling.ejbservice.facade.SystemFacade;
import hu.qwaevisz.tickethandling.ejbservice.facade.TicketFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.StatusStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.weblayer.common.FormValue;
import hu.qwaevisz.tickethandling.weblayer.common.ListAttribute;
import hu.qwaevisz.tickethandling.weblayer.common.ListParameter;
import hu.qwaevisz.tickethandling.weblayer.common.Page;

@WebServlet("/TicketList")
public class TicketListController extends HttpServlet implements ListAttribute, ListParameter, FormValue {

	private static final long serialVersionUID = -1977646750178615187L;

	private static final Logger LOGGER = Logger.getLogger(TicketListController.class);

	@EJB
	private TicketFacade ticFacade;

	@EJB
	private SystemFacade sysFacade;

	@EJB
	private EmployeeFacade empFacade;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get tickets for list");

		final String priorityName = request.getParameter(PRIORITY);
		final String statusName = request.getParameter(STATUS);
		final String systemId = request.getParameter(SYSTEM);
		final String processorId = request.getParameter(PROCESSOR);
		final String level = request.getParameter(LEVEL);

		TicketCriteria criteria = new TicketCriteria();

		if (priorityName != null && !priorityName.equals(FILTER_ALL)) {
			criteria.setPriority(PriorityStub.valueOf(priorityName));
		}

		if (statusName != null && !statusName.equals(FILTER_ALL)) {
			criteria.setStatus(StatusStub.valueOf(statusName));
		}

		if (systemId != null && !systemId.equals(FILTER_ALL)) {
			criteria.setSystem(systemId);
		}

		if (processorId != null && !processorId.equals(FILTER_ALL)) {
			criteria.setProcessorId(processorId);
		}

		if (level != null && !level.equals(FILTER_ALL)) {
			criteria.setLevel(Integer.parseInt(level));
		}

		request.setAttribute(ATTR_SYSTEM, systemId);
		request.setAttribute(ATTR_PRIORITY, priorityName);
		request.setAttribute(ATTR_STATUS, statusName);
		request.setAttribute(ATTR_PROCESSOR, processorId);
		request.setAttribute(ATTR_LEVEL, level);

		try {
			request.setAttribute(ATTR_SYSLIST, this.sysFacade.getSysLabels());
			request.setAttribute(ATTR_EMPLIST, this.empFacade.getEmpLabels());

			List<TicketStub> tickets = this.ticFacade.getTickets(criteria);

			request.setAttribute(ATTR_TICKETS, tickets);

		} catch (FacadeException e) {
			LOGGER.error(e, e);
		}

		final RequestDispatcher view = request.getRequestDispatcher(Page.LIST.getJspName());
		view.forward(request, response);
	}

}
