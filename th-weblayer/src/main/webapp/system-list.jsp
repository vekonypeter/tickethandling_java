<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.List"%>
<%@ page
	import="hu.qwaevisz.tickethandling.weblayer.common.SystemListAttribute" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://qwaevisz.hu/jsp/tlds/tickettag" prefix="bt"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="header.html"></jsp:include>
	<% boolean isAdmin = request.isUserInRole("admin"); %>
</head>
<body>

	<jsp:include page="navbar.jsp"></jsp:include>

	<div class="jumbotron jumbotron-ticketing">
		<bt:header>List of Systems</bt:header>
	</div>
	<div class="container table-ticketing">
		<h3>Systems
		<% if (isAdmin) { %>
			<a href="CustomerCreate"><span class="glyphicon glyphicon-plus newmessage-button" style="float:right"></span></a>
		<% } %>
		</h3>
		<span class="line-ticketing"></span>
		<c:choose>
			<c:when test="${requestScope.systems.isEmpty()}">
				<div class="row">
					<div class="col-md-12">
						System list is <strong>empty</strong>.
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="col-sm-2 strong">System ID</div>
					<div class="col-sm-3 strong">Company name</div>
					<div class="col-sm-7 strong">Installed Components</div>
				</div>

				<c:forEach items="${requestScope.systems}" var="system">
					<div class="row">
						<div class="col-sm-2">
							<strong><c:out value="${system.id}" /></strong>
							<% if (isAdmin) { %>
							<a href="CustomerDelete?systemId=<c:out value="${system.id}" />"><span class="glyphicon glyphicon-trash"></span></a>
							<% } %>
						</div>
						<div class="col-sm-3">
							<c:out value="${system.company_name}" />
						</div>
						<div class="col-sm-7">
							<c:forEach items="${system.components}" var="comp">
								<c:out value="${comp.label}" />,
							</c:forEach>
							<% if (isAdmin) { %>
								<a href="ComponentAssign?systemId=<c:out value="${system.id}" />">Edit&nbsp;<span class="glyphicon glyphicon-edit"></span></a>
							<% } %>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>