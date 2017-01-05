package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response
import com.google.gson.Gson
import java.util.Calendar
//import java.util.Date
import java.util.UUID
import java.sql.Timestamp
import org.psnc.kmodernlrs.models.*
import org.psnc.kmodernlrs.gson.GsonFactoryProvider
import org.psnc.kmodernlrs.ApiEndpoints
import org.psnc.kmodernlrs.services.StatementService
import org.springframework.beans.factory.annotation.Autowired

const val JSON_TYPE:String = "application/json"

@Component
@Path(ApiEndpoints.STATEMENTS_ENDPOINT)
open class StatementsController {

	@Autowired lateinit var service: StatementService
	
	val log: Logger = LoggerFactory.getLogger(StatementsController::class.java)
	
	lateinit var gson:Gson
	
	@Autowired
	fun setGsonProvider(gsonFactory: GsonFactoryProvider){
		gson = gsonFactory.gsonFactory()
	}
	
	@GET
	@Produces(JSON_TYPE)
	@Path("/{statementId}")
	fun getStatement(@PathParam("statementId") statementId: String) : String {
		log.debug(">>> get statement with id: "+ statementId)
		val statement = service.getStatement(statementId)
		if(statement != null) {
			return gson.toJson(statement)
		} else {
			return "No statement found"
		}
	}
	
	@GET
	@Produces(JSON_TYPE)
	fun getAllStatements() : String {
		return gson.toJson(service.getAll())
	}

	@PUT
	@Path("/{statementId}")
	@Consumes(JSON_TYPE)
	fun putStatementWithId(@PathParam("statementId") statementId: String, json: String) : Response {
		val response:Response?
		if(!json.isNullOrEmpty()){
			val statement: Statement = gson.fromJson(json, Statement::class.java)
			if(statementId.isNullOrEmpty()) {
				response = Response.noContent().build()
			} else if(statement.id.isNullOrEmpty()) {
				statement.id = statementId
				registerStatement(statement)
				response = Response.ok().build()
			} else if(statement.id == statementId) {
				registerStatement(statement)
				response = Response.ok().build()
			} else {
				response = Response.status(Response.Status.CONFLICT).build()
			}
		} else {
			response = Response.noContent().build()
		}

		return response
	}

	@POST
	@Consumes(JSON_TYPE)
	fun postStatement(json: String) : Response {
		val response:Response?
		if(!json.isNullOrEmpty()){
			val statement: Statement = gson.fromJson(json, Statement::class.java)
			if(statement.id.isNullOrEmpty()) {
				statement.id = UUID.randomUUID().toString()
			}
			registerStatement(statement)
			val statementId:String = statement.id
			response = Response.ok("Statement ID: "+ statementId).build()
		} else {
			response = Response.noContent().build()
		}
		return response
	}

	fun registerStatement(statement: Statement) {
		log.debug(String.format(">>> Saving Statement: %s", statement))

		statement.stored = Timestamp(Calendar.getInstance().getTime().getTime()).toString()
		service.createStatement(statement)

	}
}