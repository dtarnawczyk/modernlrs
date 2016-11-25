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
import org.springframework.beans.factory.annotation.Autowired

const val JSON_TYPE:String = "application/json"

@Component
@Path("/xAPI/statements")
open class StatementsController {
	
	val log = LoggerFactory.getLogger(StatementsController::class.java)
	
	@Autowired
	lateinit var statement:Statement
	
//	@GET
//	fun statementsList(): String {
//		return "List of statements"
//	}
	
	@GET
	@Produces(JSON_TYPE)
	fun getStmnt(): Statement {
		statement = Statement("1", "1.0")
		log.debug(">>>>>>>>>> Json object id: ", statement.id)
		log.debug(">>>>>>>>>> Json object version: ", statement.version)
		log.debug(">>>>>>>>>> Json toString: ", statement.toString())
		return statement
	}
	
	@POST
	@Consumes(JSON_TYPE)
	@Produces(JSON_TYPE)
	fun register(@FormParam("inputData") inputData: String) {
		var statement: Statement = Gson().fromJson(inputData, Statement::class.java)
	}
}