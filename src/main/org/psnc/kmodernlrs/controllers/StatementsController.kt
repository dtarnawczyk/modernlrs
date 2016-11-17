package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/xAPI/statements")
class StatementsController {
	
	@GET
	fun statementsList() : String {
		return "List of statements";
	}
}