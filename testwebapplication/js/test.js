		var SYSTEMID = 'RTS-758';
		var SYSTEMS = [];
		
		function getMyTickets() {
			$.ajax({
				type: 'get',
				url: 'http://localhost:8080/th-webservice/api/ticket/system/' + SYSTEMID,
				success: function(result){
					$('#status').html("Tickets successfully loaded!");
					$('#ticketContainer').html("");
					for (var i = 0; i < result.length; i++) {
						$('#ticketContainer').html($('#ticketContainer').html() + "<li onclick='showTicketDetails(\"" + result[i].id + "\")'>" + result[i].id + "</li>");
						SYSTEMS.push(result[i].id);
					}					
				},
				error: function(result) {
					$('#status').html("Sorry, we can't reach your tickets right now :(");
				}
			});	
		}
		
		function sendMessage() {
			
			var ticketId = $('#tic-det-id').html();
			var message = $('#message').val();
			
			if (SYSTEMS.indexOf(ticketId.trim().toUpperCase()) != -1) {		
				if (message.trim() !=  "") {		
					data = { 'ticketId' : ticketId, 'message' : message },
                    
                    $('#status').html("Sending new message...");
					
					$.ajax({
						url: 'http://localhost:8080/th-webservice/api/ticket/send',
						type: 'PUT', 
					    dataType: 'json', 
					    data: JSON.stringify(data), 
					    contentType: 'application/json',
						success: function(result){
							$('#status').html("Your message was successfully delivered!");	
							$('#ticketId').val("");
							$('#message').val("");
							
							showTicketDetails(ticketId);
						},
						error: function(result) {
							$('#status').html("Something happened, your message was not delivered!");
						}
					});
				} else {
					$('#status').html("Message field cannot be blank!");
				}		
			} else {
				$('#status').html("Choose a ticket ID from list!");
			}
		}
		
		function createTicket() {
			
			var prio =  $('#prio').val();
			var steps = $('#steps').val();
			var sender = $('#sender').val();
			var impact = $('#impact').val();
			var desc = $('#desc').val();
			
			data = { 'systemId' : SYSTEMID, 'priority' : prio, 'sender_name' : sender,  'business_impact': impact, 'steps_to_rep': steps, 'initialMessage': desc},
            
            $('#status').html("Creating new ticket...");
			
			$.ajax({
				url: 'http://localhost:8080/th-webservice/api/ticket/create',
				type: 'POST', 
			    dataType: 'json', 
			    data: JSON.stringify(data), 
			    contentType: 'application/json',
				success: function(result){
					window.location.href = "index.html";
				},
				error: function(result) {
					$('#status').html("Something happened, your ticket was not created!");
				}
			});
		}
		
		function showTicketDetails(ticketId) {
			if (SYSTEMS.indexOf(ticketId.trim().toUpperCase()) != -1) {
                
                $('#status').html("Getting details of ticket " + ticketId + "...");
            
				$.ajax({
					type: 'get',
					url: 'http://localhost:8080/th-webservice/api/ticket/' + ticketId,
					success: function(result){
						
						last = new Date(result.lastchanged);
						created = new Date(result.creationdate);
						
						$('#tic-det-id').html(result.id);
						$('#tic-det-prio').html(result.priority);
						$('#tic-det-status').html(result.status);
						$('#tic-det-proc').html(result.processor.name);
						$('#tic-det-last').html(last.toLocaleDateString() + " " + last.toLocaleTimeString());
						$('#tic-det-created').html(created.toLocaleDateString() + " " + created.toLocaleTimeString());
						$('#tic-det-del').attr("onClick", "deleteTicket(\"" + ticketId + "\")");
						
						$('#tic-det-msgs').html("");
						
						for (i = 0; i < result.conversation.length; i++) {
							msg = result.conversation[i];
							
							on = new Date(msg.date);
							$('#tic-det-msgs').html(
									$('#tic-det-msgs').html() +
									"From: " + msg.from + 
									" To: " + msg.to + 
									" on " + on.toLocaleTimeString() +" "+on.toLocaleDateString() + "<br />" +
									"<i>" + msg.text + "</i><br /><br />")
						}
                        
                        $('#status').html("Details loaded!");
											
					},
					error: function(result) {
						$('#status').html("Sorry, we can't reach your ticket's details right now :(");
					}
				});					
			} else {
				$('#status').html("The Ticket ID you specified is not on the list!");
			}
		}
		
		
		
		function deleteTicket(ticketId) {			
			if (SYSTEMS.indexOf(ticketId.trim().toUpperCase()) != -1) {
								
				if (confirm("You are about to delete ticket " + ticketId + " Are you sure?") == true) {

					$.ajax({
						type: 'delete',
						url: 'http://localhost:8080/th-webservice/api/ticket/' + ticketId,
						success: function(result){
							
							$('#status').html("The ticket was successfully deleted! The page will reload in 3 seconds...");
							
							setTimeout(function() {location.href = "index.html"}, 3000);
																			
						},
						error: function(result) {
							$('#status').html("Sorry, we can't delete your ticket right now :(");
						}
					});			
				} 				
				
			} else {
				$('#status').html("The Ticket ID you specified is not on the list!");
			}
		}