package hu.qwaevisz.tickethandling.webservice.mapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import hu.qwaevisz.tickethandling.ejbservice.exception.AdaptorException;
import hu.qwaevisz.tickethandling.webservice.filter.TickethandlingCrossOriginRequestFilter;

@Provider
public class AdaptorExceptionMapper implements ExceptionMapper<AdaptorException> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(final AdaptorException e) {
		return Response.status(e.getErrorCode().getHttpStatusCode()).entity(e.build()) //
				.header(TickethandlingCrossOriginRequestFilter.ALLOW_ORIGIN, "*") //
				.header(TickethandlingCrossOriginRequestFilter.ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS, HEAD") //
				.header(TickethandlingCrossOriginRequestFilter.MAX_AGE, "1209600") //
				.header(TickethandlingCrossOriginRequestFilter.ALLOW_HEADERS, "x-requested-with, origin, content-type, accept, X-Codingpedia, authorization") //
				.header(TickethandlingCrossOriginRequestFilter.ALLOW_CREDENTIALS, "true") //
				.type(MediaType.APPLICATION_JSON).build();
	}

}
