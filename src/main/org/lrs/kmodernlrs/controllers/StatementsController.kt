package org.lrs.kmodernlrs.controllers

//import java.util.Date
import com.google.gson.Gson
import org.lrs.kmodernlrs.ApiEndpoints
import org.lrs.kmodernlrs.event.XapiEvent
import org.lrs.kmodernlrs.event.XapiEventData
import org.lrs.kmodernlrs.gson.GsonFactoryProvider
import org.lrs.kmodernlrs.models.Statement
import org.lrs.kmodernlrs.security.UserAccount
import org.lrs.kmodernlrs.services.StatementService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

const val JSON_TYPE:String = "application/json"

@Component
@Path(ApiEndpoints.STATEMENTS_ENDPOINT)
open class StatementsController {

	@Autowired lateinit var service: StatementService

	@Autowired lateinit var eventPublisher: ApplicationEventPublisher
	
	val log: Logger = LoggerFactory.getLogger(StatementsController::class.java)
	
	lateinit var gson: Gson
	
	@Autowired
	fun setGsonProvider(gsonFactory: GsonFactoryProvider){
		gson = gsonFactory.gsonFactory()
	}
	
	@GET
	@Produces(JSON_TYPE)
	@Path("/{statementId}")
	@Cacheable("statements")
	fun getStatement(@Context request: HttpServletRequest, @Context context: SecurityContext,
                     @PathParam("statementId") statementId: String) : ResponseEntity<String> {
		log.debug(">>> get statement with id: $statementId")
		val statement = service.getStatement(statementId)
		if(statement != null) {
			statementEventCalled(request, context, statement)
			return ResponseEntity(gson.toJson(statement), HttpStatus.OK)
		} else {
			return ResponseEntity("No statement found", HttpStatus.NOT_FOUND)
		}
	}
	
	@GET
	@Produces(JSON_TYPE)
	@Cacheable("statements")
	fun getAllStatements(@Context request: HttpServletRequest, @Context context: SecurityContext)
			: ResponseEntity<String> {
		val statementList : List<Statement>? = service.getAll()
//		statementList?.let {
//			statementEventCalled(request, context, it)
//		}
//		return gson.toJson(statementList)
		if(statementList != null) {
			statementEventCalled(request, context, statementList)
            return ResponseEntity(gson.toJson(statementList), HttpStatus.OK)
		} else {
            return ResponseEntity("No statements found", HttpStatus.NOT_FOUND)
		}
	}

	@PUT
	@Path("/{statementId}")
	@Consumes(JSON_TYPE)
	@CachePut("statements")
	fun putStatementWithId(@Context request: HttpServletRequest, @Context context: SecurityContext,
                           @PathParam("statementId") statementId: String, json: String) : Response {
		var response: Response?
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
		var response: Response?
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
		log.debug(">>> Saving Statement: $statement")

		statement.stored = Timestamp(Calendar.getInstance().time.time).toString()
		service.createStatement(statement)
		statementEventCalled(request, context, statement)

	}

	fun statementEventCalled(request: HttpServletRequest, context: SecurityContext,
                             statement: Any) {
		val remoteIp = request.remoteAddr
		val method = request.method
		var userName = ""
		if(context.userPrincipal is UsernamePasswordAuthenticationToken){
			val userPassAuthToken = context.userPrincipal as UsernamePasswordAuthenticationToken
			userName = (userPassAuthToken.principal as UserAccount).name
		} else {
			userName = context.userPrincipal.name
		}
		val currentTime = Timestamp(Calendar.getInstance().time.time).toString()
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