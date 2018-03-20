<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page
	import="hu.qwaevisz.tickethandling.ejbserviceclient.domain.TicketStub"%>
<%@ page
	import="hu.qwaevisz.tickethandling.ejbserviceclient.domain.PriorityStub"%>
	<%@ page
	import="hu.qwaevisz.tickethandling.ejbserviceclient.domain.MessageStub"%>
<%@ page
	import="hu.qwaevisz.tickethandling.ejbserviceclient.domain.StatusStub"%>
<%@ page
	import="hu.qwaevisz.tickethandling.ejbserviceclient.domain.EmployeeStub"%>
<%@ page
	import="hu.qwaevisz.tickethandling.ejbserviceclient.domain.SystemStub"%>
<%@ page
	import="hu.qwaevisz.tickethandling.weblayer.common.TicketAttribute"%>
<%@ page
	import="hu.qwaevisz.tickethandling.weblayer.common.TicketParameter"%>
<%@ page
	import="hu.qwaevisz.tickethandling.persistence.entity.Message"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://qwaevisz.hu/jsp/tlds/tickettag" prefix="bt"%>
<%
	TicketStub ticket = (TicketStub) request.getAttribute(TicketAttribute.ATTR_TICKET);
	boolean isProcessor = ticket.getProcessor().getId().equals(request.getUserPrincipal().getName());
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.html"></jsp:include>
</head>
<body>

	<jsp:include page="navbar.jsp"></jsp:include>

	<div class="jumbotron jumbotron-ticketing">
		<h1>
			<%
				out.print(ticket.getId());
			%>
		</h1>
	</div>
	<div class="container table-ticketing">
		<div class="row">
			<div class="col-md-6">
				<h3>Overview</h3>
				<span class="line-ticketing"></span>
				<form method="POST" action="Ticket">
					<input type="text" name="id" id="id" hidden="hidden" contenteditable="false" value="<%out.print(ticket.getId());%>" />

					<div class="form-group">
						<label class="control-label" for="system">System: </label>
						<input class="form-control"  type="text" name="system" id="system" disabled="disabled" value="<%out.print(ticket.getSystem().getId());%>"/>
					</div>
					
					<div class="form-group">
						<label class="control-label" for="company">Company: </label>
						<input class="form-control"  type="text" name="company" id="company" disabled="disabled" value="<%out.print(ticket.getSystem().getCompany_name());%>"/>
					</div>

					<div class="form-group">
						<label class="control-label" for="sender_name">Sender name: </label>
						<input class="form-control" type="text" name="sender_name" id="sender_name" disabled="disabled" value="<%out.print(ticket.getSender_name());%>"/>
					</div>

					<div class="form-group">
						<label class="control-label" for="processor">Processor: </label>
						<input class="form-control"  type="text" name="processor" id="processor" disabled="disabled" value="<%out.print(ticket.getProcessor().getName() + " / " + ticket.getProcessor().getId());%>"/>
					</div>
					
					<div class="form-group">
						<label class="control-label" for="business_impact">Business	impact: </label>
						<input class="form-control" type="text" name="business_impact" id="business_impact" disabled="disabled" value="<%out.print(ticket.getBusiness_impact());%>"/>
					</div>
					
					<div class="form-group">
						<label class="control-label" for="steps_to_rep">Steps to
							reproduce the issue: </label> <input class="form-control" type="text"
							name="steps_to_rep" id="steps_to_rep"
							value="<%out.print(ticket.getSteps_to_rep());%>"
							disabled="disabled" />
					</div>
					
					<div class="form-group">
						<label class="control-label" for="creationdate">Creation date: </label>
						<input class="form-control" type="text" name="creationdate" id="creationdate" disabled="disabled" value="<%out.print(ticket.getCreationdate());%>"/>
					</div>
					
					<div class="form-group">
						<label class="control-label" for="lastchanged">Last changed: </label>
						<input class="form-control" type="text" name="lastchanged" id="lastchanged" disabled="disabled" value="<%out.print(ticket.getLastchanged());%>"/>
					</div>
					
					<div class="form-group">
						<label class="control-label" for="level">Level: </label>
						<input class="form-control" type="number" name="level" id="level" max="3" min="1" value="<%out.print(ticket.getLevel());%>" <% out.print(isProcessor ? "" : "disabled=\"disabled\""); %>/>
						<% if (isProcessor) { %>
						<p><br /><i>Note that, if you change the level you will be no longer the processor of the ticket!</i></p>
						<%
							}
						%>
					</div>
					
					
					<div class="form-group">
						<label class="control-label" for="priority">Priority: </label>
						<select	class="form-control" name="priority" id="priority" <% out.print(isProcessor ? "" : "disabled=\"disabled\""); %>>
							<%
								for (PriorityStub priority : PriorityStub.values()) {
							%>
							<option value="<%out.print(priority.name());%>"
								<%out.print(priority == ticket.getPriority() ? "selected=\"selected\"" : "");%>>
								<%
									out.print(priority.getLabel());
								%>
							</option>
							<%
								}
							%>
						</select>
					</div>
					
					<div class="form-group">
						<label class="control-label" for="status">Status: </label>
						<select	class="form-control" name="status" id="status" <% out.print(isProcessor ? "" : "disabled=\"disabled\""); %>>
							<%
								for (StatusStub status : StatusStub.values()) {
							%>
							<option value="<%out.print(status.name());%>"
								<%out.print(status == ticket.getStatus() ? "selected=\"selected\"" : "");%>>
								<%
									out.print(status.getLabel());
								%>
							</option>
							<%
								}
							%>
						</select>

					</div>
					<% if (isProcessor) { %>
					<div class="form-group">
						<button type="submit" class="btn btn-default">Submit changes</button>
					</div>
					<%
					}
					%>
				</form>
			</div>
			<div class="col-md-6">
				<h3>Messages
				<% if (isProcessor) { %>
				<span class="glyphicon glyphicon-plus newmessage-button" style="float:right"; onclick="$('#newmessagebox-ticketing').slideToggle()"></span>
				<% } %>
				</h3>
				<span class="line-ticketing"></span>
				<% if (isProcessor) { %>
				<div id="newmessagebox-ticketing">
						<form method="POST" action="Ticket" class="form-horizontal">
							<input type="text" name="id" id="id" hidden="hidden" contenteditable="false" value="<%out.print(ticket.getId());%>" />
						
							<div class="form-group">
								<div class="col-sm-12">
									<textarea class="form-control" rows="5" id="newmessage" name="newmessage" style="resize: none;"></textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<button type="submit" class="btn btn-default" style="float: right;">Send</button>
								</div>
							</div>
						</form>
				</div>
				<%
					}
					List<MessageStub> conversation = ticket.getConversation();
					Collections.sort(conversation); 						
				
					for (MessageStub message : conversation) {
				%>
				<div class="message-ticketing">
					<div class="row">
						<div class="col-sm-12"><h4><% out.print(message.getDate()); %></h4></div>
						<div class="col-sm-2"><strong>From:</strong></div>
						<div class="col-sm-10"><% out.print(message.getFrom()); %></div>
						<div class="col-sm-2"><strong>To:</strong></div>
						<div class="col-sm-10"><% out.print(message.getTo()); %></div>
						<div class="col-sm-12">
							<br/>
							<% out.print(message.getText()); %>
							</div>
					</div>
				</div>
				<%		
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>