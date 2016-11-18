package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType
import com.google.gson.Gson
import org.psnc.kmodernlrs.models.Statement

@Component

@Path("/xAPI/statements")
class StatementsController {
	
	@GET
	fun statementsList() : String {
		return "List of statements";
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	fun register(@FormParam("inputData") inputData: String): Response {
		var statement: Statement = Gson().fromJson(inputData, Statement::class.java)
	}
}