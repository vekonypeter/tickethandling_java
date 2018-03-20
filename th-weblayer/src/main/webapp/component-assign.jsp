<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="hu.qwaevisz.tickethandling.ejbserviceclient.domain.ComponentStub" %>
<%@ page import="hu.qwaevisz.tickethandling.weblayer.common.ComponentAssignAttribute" %>
	
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
		<h1>Assign Components to System</h1>
	</div>
	<div class="container table-ticketing">
		<h3>Select components to assign with system <c:out value="${requestScope.system.id}" /></h3>
		<span class="line-ticketing"></span>
		
		<form method="post" action="ComponentAssign" class="form-horizontal">
			<input type="text" name="systemId" value="${requestScope.system.id}" hidden>
		
			<c:set var="componentValues" value="<%=ComponentStub.values()%>" />
			<c:forEach items="${componentValues}" var="component">
				<div class="checkbox">
			 	 	<label><input type="checkbox" name="components" value="${component.name}" ${requestScope.system.components.contains(component) ? "checked" : ""}>
			 	 		${component.label}
			 	 	</label>
				</div>
			</c:forEach>
			<br />
			<div class="form-group">
				<div class="col-sm-12">
					<button type="submit" class="btn btn-default">Submit</button>&nbsp;&nbsp;<a href="SystemList">Cancel</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>