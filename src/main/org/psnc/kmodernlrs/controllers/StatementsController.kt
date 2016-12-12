package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.google.gson.JsonSerializer
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

import org.psnc.kmodernlrs.models.*
import org.psnc.kmodernlrs.serializers.*
import org.psnc.kmodernlrs.gson.GsonFactoryProvider
import org.psnc.kmodernlrs.ApiEndpoints
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.psnc.kmodernlrs.repo.Repository

const val JSON_TYPE:String = "application/json"

@Component
@Path(ApiEndpoints.STATEMENTS_ENDPOINT)
open class StatementsController {
	
	@Autowired lateinit var repo: Repository<Statement>
	
	val log = LoggerFactory.getLogger(StatementsController::class.java)
	
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
		var statement = repo.get(statementId)
		if(statement != null) {
			return gson.toJson(statement)
		} else {
			return "No statement found"
		}
	}
	
	@GET
	@Produces(JSON_TYPE)
	fun getAllStatements() : String {
		return gson.toJson(repo.getAll())
	}
	
	@POST
	@Consumes(JSON_TYPE)
	@Produces(JSON_TYPE)
	fun register(json: String): Statement {
		var statement: Statement = gson.fromJson(json, Statement::class.java)
		log.debug(String.format(">>> Saving Statement: %s", statement))
		repo.add(statement.id, statement)
		return statement
	}
	
}