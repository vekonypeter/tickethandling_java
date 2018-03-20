<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="hu.qwaevisz.tickethandling.weblayer.common.HomeAttribute"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://qwaevisz.hu/jsp/tlds/tickettag" prefix="bt"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="header.html"></jsp:include>
</head>
<body>

	<jsp:include page="navbar.jsp"></jsp:include>

	<div class="jumbotron jumbotron-ticketing">
		<h1>Home</h1>
	</div>
	<div>
		<c:choose>
			<c:when test="${requestScope.message != null}">
				<div class="row">
					<div class="col-md-12">
						<c:out value="${ticket.message}" />
					</div>
				</div>
			</c:when>
		</c:choose>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-12" style="text-align: right; margin-bottom: 5px;">
				<a href="TicketCreate"><strong>Create</strong></a> new ticket.
			</div>
		</div>
	</div>
	<div class="container table-ticketing">
		<h3>My tickets</h3>
		<span class="line-ticketing"></span>
		<c:choose>
			<c:when test="${requestScope.tickets.isEmpty()}">
				<div class="row">
					<div class="col-md-12">
						You have no tickets!
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="col-sm-3 strong">ID</div>
					<div class="col-sm-1 strong">System</div>
					<div class="col-sm-1 strong">Priority</div>
					<div class="col-sm-2 strong">Status</div>
					<div class="col-sm-2 strong">Created on</div>
					<div class="col-sm-2 strong">Last changed on</div>
					<div class="col-sm-1 strong">Actions</div>
				</div>

				<c:forEach items="${requestScope.tickets}" var="ticket">
					<div class="row">
						<div class="col-sm-3">
							<a href="Ticket?id=<c:out value="${ticket.id}" />"><c:out
									value="${ticket.id}" /></a>
						</div>
						<div class="col-sm-1">
							<c:out value="${ticket.system.id}" />
						</div>
						<div class="col-sm-1">
							<c:out value="${ticket.priority.label}" />
						</div>
						<div class="col-sm-2">
							<c:out value="${ticket.status.label}" />
						</div>
						<div class="col-sm-2">
							<c:out value="${ticket.creationdate}" />
						</div>
						<div class="col-sm-2">
							<c:out value="${ticket.lastchanged}" />
						</div>
						<div class="col-sm-1">
							<a href="TicketDelete?id=<c:out value="${ticket.id}" />"><span class="glyphicon glyphicon-trash"></span></a>
							<a href="UserAction?ticket_id=<c:out value="${ticket.id}" />&give_back=1"><span class="glyphicon glyphicon-arrow-down"></span></a>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="container table-ticketing">
		<h3>Unassigned tickets on my level</h3>
		<span class="line-ticketing"></span>
		<c:choose>
			<c:when test="${requestScope.unassigned.isEmpty()}">
				<div class="row">
					<div class="col-md-12">
						No unassigned tickets on your level!
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="col-sm-3 strong">ID</div>
					<div class="col-sm-1 strong">System</div>
					<div class="col-sm-1 strong">Priority</div>
					<div class="col-sm-2 strong">Status</div>
					<div class="col-sm-2 strong">Level</div>
					<div class="col-sm-2 strong">Last changed on</div>
					<div class="col-sm-1 strong">Actions</div>
				</div>

				<c:forEach items="${requestScope.unassigned}" var="ticket">
					<div class="row">
						<div class="col-sm-3">
							<a href="Ticket?id=<c:out value="${ticket.id}" />"><c:out
									value="${ticket.id}" /></a>
						</div>
						<div class="col-sm-1">
							<c:out value="${ticket.system.id}" />
						</div>
						<div class="col-sm-1">
							<c:out value="${ticket.priority.label}" />
						</div>
						<div class="col-sm-2">
							<c:out value="${ticket.status.label}" />
						</div>
						<div class="col-sm-2">
							<c:out value="${ticket.level}" />
						</div>
						<div class="col-sm-2">
							<c:out value="${ticket.lastchanged}" />
						</div>
						<div class="col-sm-1">
							<a href="UserAction?ticket_id=<c:out value="${ticket.id}" />&assign=1">
							<span class="glyphicon glyphicon-arrow-up"></span></a>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>