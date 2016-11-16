package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("/xAPI/statements")
class StatementsController {
	
	@GET
	fun statementsList() : String {
		return "List of statements";
	}
}