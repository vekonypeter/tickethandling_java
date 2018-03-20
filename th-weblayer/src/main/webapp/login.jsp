<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="hu.qwaevisz.tickethandling.weblayer.common.LoginAttribute"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="header.html"></jsp:include>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>

	<div class="jumbotron jumbotron-ticketing">
		<h1>Login</h1>
	</div>
	<div class="container">
		<%
			String userId = (String) request.getAttribute(LoginAttribute.ATTR_USERID);
			String errorMessage = (String) request.getAttribute(LoginAttribute.ATTR_ERROR);
		%>

		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<form action="j_security_check" method="post">
					<div class="alert alert-danger fade in" <% out.print(errorMessage == "" ? "style=\"display: none;\"" : "");%>>
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<span><%=errorMessage%></span>
					</div>

					<div class="form-group">
						<label for="userid">User ID:</label> <input type="text"
							class="form-control" name="j_username" id="userid"
							value="<%=userId%>" />
					</div>
					<div class="form-group">
						<label for="password">Password: </label> <input type="password"
							class="form-control" name="j_password" id="password" />
					</div>
					<button type="submit" class="btn btn-default">Login</button>
				</form>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>
</body>
</html>

