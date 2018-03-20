<div class="navbar navbar-inverse navbar-fixed-top navbar-ticketing" data-spy="affix" data-offset-top="50">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav nav-custom">
				<li><a href="Home"><span class="glyphicon glyphicon-home" style="margin-right: 6px"></span>Home</a></li>
				<li><a href="TicketList"><span class="glyphicon glyphicon-list" style="margin-right: 6px"></span>Tickets</a></li>
				<li><a href="SystemList"><span class="glyphicon glyphicon-cog" style="margin-right: 6px"></span>Systems</a></li>
				<li><a href="Logout"><span class="glyphicon glyphicon-log-out" style="margin-right: 6px"></span>Log out</a></li>
		</ul>
		<span class="username"><% out.print(request.getUserPrincipal() != null ? "Logged in as <strong>" + (String) request.getUserPrincipal().getName() + "</strong>": "You are not logged in"); %></span>
		</div>
	</div>
</div>
