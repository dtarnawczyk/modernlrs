package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.core.Response
import com.google.gson.Gson
import org.psnc.kmodernlrs.models.Statement
//import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity

const val JSON_TYPE:String = "application/json"

@Component
@Path("/xAPI/statements")
open class StatementsController {
	
	val log = LoggerFactory.getLogger(StatementsController::class.java)
	
	@GET
	@Produces(JSON_TYPE)
	fun getStmnt() = Statement("2222", "2222.0")
	
	
	@POST
	@Consumes(JSON_TYPE)
	@Produces(JSON_TYPE)
	fun register(json: String): Statement {
		log.debug(">>> Input json: " + json)
		var statement: Statement = Gson().fromJson(json, Statement::class.java)
		log.debug(">>> statement: " + statement)
		return statement
	}
}