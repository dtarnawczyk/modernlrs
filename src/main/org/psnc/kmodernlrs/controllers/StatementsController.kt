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
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext
import com.google.gson.Gson
import java.util.Calendar
//import java.util.Date
import java.util.UUID
import java.sql.Timestamp
import org.psnc.kmodernlrs.models.*
import org.psnc.kmodernlrs.gson.GsonFactoryProvider
import org.psnc.kmodernlrs.ApiEndpoints
import org.psnc.kmodernlrs.event.XapiEventData
import org.psnc.kmodernlrs.event.XapiEvent
import org.psnc.kmodernlrs.security.UserAccount
import org.psnc.kmodernlrs.services.StatementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.security.Principal
import javax.servlet.http.HttpServletRequest

const val JSON_TYPE:String = "application/json"

@Component
@Path(ApiEndpoints.STATEMENTS_ENDPOINT)
open class StatementsController {

	@Autowired lateinit var service: StatementService

	@Autowired lateinit var eventPublisher: ApplicationEventPublisher
	
	val log: Logger = LoggerFactory.getLogger(StatementsController::class.java)
	
	lateinit var gson:Gson
	
	@Autowired
	fun setGsonProvider(gsonFactory: GsonFactoryProvider){
		gson = gsonFactory.gsonFactory()
	}
	
	@GET
	@Produces(JSON_TYPE)
	@Path("/{statementId}")
	@Cacheable("statements")
	fun getStatement(@Context request: HttpServletRequest, @Context context: SecurityContext,
					 @PathParam("statementId") statementId: String) : String {
		log.debug(">>> get statement with id: "+ statementId)
		val statement = service.getStatement(statementId)
		if(statement != null) {
			statementEventCalled(request, context, statement)
			return gson.toJson(statement)
		} else {
			return "No statement found"
		}
	}
	
	@GET
	@Produces(JSON_TYPE)
	@Cacheable("statements")
	fun getAllStatements(@Context request: HttpServletRequest, @Context context: SecurityContext) : String {
		val statementList : List<Statement>? = service.getAll()
//		statementList?.let {
//			statementEventCalled(request, context, it)
//		}
//		return gson.toJson(statementList)
		if(statementList != null) {
			statementEventCalled(request, context, statementList)
			return gson.toJson(statementList)
		} else {
			return "No statements found"
		}
	}

	@PUT
	@Path("/{statementId}")
	@Consumes(JSON_TYPE)
	@CachePut("statements")
	fun putStatementWithId(@Context request: HttpServletRequest, @Context context: SecurityContext,
						   @PathParam("statementId") statementId: String, json: String) : Response {
		var response:Response?
		if(!json.isNullOrEmpty()){
			val statement: Statement = gson.fromJson(json, Statement::class.java)
			if(statementId.isNullOrEmpty()) {
				response = Response.noContent().build()
			} else if(statement.id.isNullOrEmpty()) {
				statement.id = statementId
				registerStatement(request, context, statement)
				response = Response.ok().build()
			} else if(statement.id == statementId) {
				registerStatement(request, context, statement)
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
	@CachePut("statements")
	fun postStatement(@Context request: HttpServletRequest, @Context context: SecurityContext,
					  json: String) : Response {
		var response:Response?
		if(!json.isNullOrEmpty()){
			val statement: Statement = gson.fromJson(json, Statement::class.java)
			if(statement.id.isNullOrEmpty()) {
				statement.id = UUID.randomUUID().toString()
			}
			registerStatement(request, context, statement)
			val statementId:String = statement.id
			response = Response.ok("Statement ID: "+ statementId).build()
		} else {
			response = Response.noContent().build()
		}
		return response
	}

	fun registerStatement(request: HttpServletRequest, context: SecurityContext,
						  statement: Statement) {
		log.debug(String.format(">>> Saving Statement: %s", statement))

		statement.stored = Timestamp(Calendar.getInstance().getTime().getTime()).toString()
		service.createStatement(statement)
		statementEventCalled(request, context, statement)

	}

	fun statementEventCalled(request: HttpServletRequest, context: SecurityContext,
							 statement: Any) {
		val remoteIp = request.remoteAddr
		val method = request.method
		var userName = ""
		if(context.userPrincipal is UsernamePasswordAuthenticationToken){
			var userPassAuthToken = context.userPrincipal as UsernamePasswordAuthenticationToken
			userName = (userPassAuthToken.principal as UserAccount).name
		} else {
			userName = context.userPrincipal.name
		}
		val currentTime = Timestamp(Calendar.getInstance().getTime().getTime()).toString()
		var xapiData: XapiEventData?
		if(statement is List<*>) {
			var ids: MutableList<String> = mutableListOf()
			(statement as List<*>).forEach {
				ids.add((it as Statement).id)
			}
			xapiData = XapiEventData("Statement", ids)
		} else {
			xapiData = XapiEventData("Statement", (statement as Statement).id)
		}
		val event = XapiEvent(userName, currentTime, xapiData , method, remoteIp)
		eventPublisher.publishEvent(event)
	}
}