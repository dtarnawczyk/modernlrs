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
import org.springframework.http.HttpEntity
import org.psnc.kmodernlrs.repo.StatementRepository
import org.apache.commons.lang3.StringUtils
import java.util.UUID

const val JSON_TYPE:String = "application/json"

@Component
@Path("/xAPI/statements")
open class StatementsController() {
	
	@Autowired lateinit var repo: StatementRepository
	
	val log = LoggerFactory.getLogger(StatementsController::class.java)
	
	@GET
	@Produces(JSON_TYPE)
	fun getStmnt() = Gson().toJson(repo.findAll())
	
	@POST
	@Consumes(JSON_TYPE)
	@Produces(JSON_TYPE)
	fun register(json: String): Statement {
		var statement: Statement = Gson().fromJson(json, Statement::class.java)
		if(StringUtils.isBlank(statement.id)) {
			statement.id = UUID.randomUUID().toString()
		} else {
			// TODO: check if exists
		}
		log.debug(">>> Saving Statement: " + statement)
		repo.save(statement)
		return statement
	}
}