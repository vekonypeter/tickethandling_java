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

import hu.qwaevisz.tickethandling.ejbservice.facade.SystemFacade;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.ComponentStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.domain.SystemStub;
import hu.qwaevisz.tickethandling.ejbserviceclient.exception.FacadeException;
import hu.qwaevisz.tickethandling.weblayer.common.ComponentAssignAttribute;
import hu.qwaevisz.tickethandling.weblayer.common.ComponentAssignParameter;
import hu.qwaevisz.tickethandling.weblayer.common.Page;

@WebServlet("/ComponentAssign")
public class ComponentAssignController extends HttpServlet implements ComponentAssignParameter, ComponentAssignAttribute {

	private static final long serialVersionUID = -4068275526750462197L;

	private static final Logger LOGGER = Logger.getLogger(ComponentAssignController.class);

	@EJB
	private SystemFacade sysFacade;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(SYSTEMID);
		SystemStub system = null;

		try {

			system = this.sysFacade.getSystem(id);

		} catch (FacadeException e) {
			LOGGER.error(e, e);
		}

		request.setAttribute(ATTR_SYSTEM, system);

		final RequestDispatcher view = request.getRequestDispatcher(Page.COMPONENT_ASSIGN.getJspName());
		view.forward(request, response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		String[] values = request.getParameterValues(COMPONENTS);

		if (values == null) {
			values = new String[0];
		}

		final String id = request.getParameter(SYSTEMID);
		SystemStub system = null;

		try {

			system = this.sysFacade.getSystem(id);
			List<ComponentStub> comps = system.getComponents();
			comps.clear();

			for (String val : values) {
				ComponentStub comp = ComponentStub.valueOf(val);
				comps.add(comp);
			}

			this.sysFacade.SaveComponents(system);

		} catch (FacadeException e) {
			LOGGER.error(e, e);
		}

		response.sendRedirect(Page.SYSTEMLIST.getUrl());
	}
}
