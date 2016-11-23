package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.core.Response
import com.google.gson.Gson
import org.psnc.kmodernlrs.models.Statement

const val JSON_TYPE:String = "application/json"

@Component
@Path("/xAPI/statements")
class StatementsController {
	
//	@GET
//	fun statementsList(): String {
//		return "List of statements"
//	}
	
	@GET
	fun getStatement(): Statement {
		return Statement("1", "1.0")
	}
	
	@POST
	@Consumes(JSON_TYPE)
	@Produces(JSON_TYPE)
	fun register(@FormParam("inputData") inputData: String) {
		var statement: Statement = Gson().fromJson(inputData, Statement::class.java)
	}
}