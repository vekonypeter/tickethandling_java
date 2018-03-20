<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<h1>New Customer</h1>
	</div>
	<div class="container">
		<form method="post" action="CustomerCreate" class="form-horizontal">
						
			<div class="form-group">
				<label class="control-label col-sm-2" for="systemId">System ID:</label>
				<div class="col-sm-2">
					<input class="form-control" name="systemId" id="systemId" value="" />				
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-2" for="name">Company name:</label>
				<div class="col-sm-10">
					<input class="form-control" name="name" id="name" value="" />				
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-2" for="address">Address:</label>
				<div class="col-sm-10">
					<input class="form-control" name="address" id="address" value="" />				
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-2" for="contact_name">Contact name:</label>
				<div class="col-sm-10">
					<input class="form-control" name="contact_name" id="contact_name" value="" />				
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-2" for="contact_email">Contact e-mail:</label>
				<div class="col-sm-10">
					<input type="email" class="form-control" name="contact_email" id="contact_email" value="" />				
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-2" for="contact_phone">Contact phone:</label>
				<div class="col-sm-4">
					<input class="form-control" name="contact_phone" id="contact_phone" value="" />				
				</div>
			</div>
	
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Submit</button>
					&nbsp;<a href="Home">Cancel</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>