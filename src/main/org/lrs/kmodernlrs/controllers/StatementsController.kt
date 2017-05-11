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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@RestController
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
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{statementId}")
	@Cacheable("statements")
	fun getStatement(@Context request: HttpServletRequest, @Context context: SecurityContext,
                     @PathParam("statementId") statementId: String) : String {
		log.debug(">>> get statement with id: $statementId")
		val statement = service.getStatement(statementId)
		if(statement != null) {
			statementEventCalled(request, context, statement)
			return gson.toJson(statement)
		} else {
			throw WebApplicationException(Response.status(HttpServletResponse.SC_NOT_FOUND).build())
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Cacheable("statements")
	fun getAllStatements(@Context request: HttpServletRequest, @Context context: SecurityContext)
			: String {
		val statementList : List<Statement>? = service.getAll()
		if(statementList != null) {
			statementEventCalled(request, context, statementList)
            return gson.toJson(statementList)
		} else {
			throw WebApplicationException(Response.status(HttpServletResponse.SC_NOT_FOUND)
					.entity("No Statements found!").build())
		}
	}

	@PUT
	@Path("/{statementId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@CachePut("statements")
	fun putStatementWithId(@Context request: HttpServletRequest,
						   @Context context: SecurityContext,
						   @PathParam("statementId") statementId: String, json: String) : Response {
		if(!json.isNullOrEmpty()){
			val statement: Statement = gson.fromJson(json, Statement::class.java)
			if(statementId.isNullOrEmpty()) {
				log.debug(">>>> statementId is empty")
				throw WebApplicationException(Response.status(HttpServletResponse.SC_NO_CONTENT)
						.entity("Parameter statementId is EMPTY!").build())
			} else if(statement.id.isNullOrEmpty() || (statement.id == statementId)) {
				statement.id = statementId
				if(service.exists(statement) ) {
					log.debug(">>>> statement already exists")
					throw WebApplicationException(Response.status(HttpServletResponse.SC_CONFLICT)
							.entity("Statement with id: $statementId already exists!").build())
				} else {
					registerStatement(request, context, statement)
					return Response.status(HttpServletResponse.SC_CREATED).build()
				}
			} else {
				throw WebApplicationException(Response.status(HttpServletResponse.SC_CONFLICT).build())
			}
		} else {
			log.debug(">>>> statement json is empty")
			throw WebApplicationException(Response.status(HttpServletResponse.SC_NO_CONTENT)
					.entity("Statement JSON is empty").build())
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@CachePut("statements")
	fun postStatement(@Context request: HttpServletRequest, @Context context: SecurityContext,
                      jsonStatement: String) : Response {
		if(!jsonStatement.isNullOrEmpty()){
			val statement: Statement = gson.fromJson(jsonStatement, Statement::class.java)
			if(statement.id.isNullOrEmpty()) {
				statement.id = UUID.randomUUID().toString()
			}
			if(service.exists(statement) ) {
				log.debug(">>>> statement already exists")
				throw WebApplicationException(Response.status(HttpServletResponse.SC_CONFLICT)
						.entity("Statement with id: ${statement.id} already exists!").build())
			} else {
				registerStatement(request, context, statement)
				val statementId:String = statement.id
				return Response.status(HttpServletResponse.SC_OK)
						.entity("Statement ID: "+ statementId).build()
			}
		} else {
			throw WebApplicationException(Response.status(HttpServletResponse.SC_NO_CONTENT)
					.entity("Statement JSON is empty").build())
		}
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
		var userName:String
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
			statement.forEach {
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