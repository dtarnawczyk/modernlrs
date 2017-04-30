package org.lrs.kmodernlrs.controllers

import com.google.gson.Gson
import org.lrs.kmodernlrs.ApiEndpoints
import org.lrs.kmodernlrs.event.XapiEvent
import org.lrs.kmodernlrs.event.XapiEventData
import org.lrs.kmodernlrs.gson.GsonFactoryProvider
import org.lrs.kmodernlrs.models.Actor
import org.lrs.kmodernlrs.security.UserAccount
import org.lrs.kmodernlrs.services.AgentsService
import org.lrs.kmodernlrs.util.InverseFunctionalIdentifierHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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
@Path(ApiEndpoints.AGENTS_ENDPOINT)
open class AgentsController {

    val log: Logger = LoggerFactory.getLogger(AgentsController::class.java)

    @Autowired lateinit var service: AgentsService
    @Autowired lateinit var eventPublisher: ApplicationEventPublisher

    lateinit var gson: Gson

    @Autowired
    fun setGsonProvider(gsonFactory: GsonFactoryProvider){
        gson = gsonFactory.gsonFactory()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Cacheable("statements")
    fun getAgents(@Context request: HttpServletRequest, @Context context: SecurityContext,
                  agentDetailsJson: String) : Response {
        if(agentDetailsJson.isNullOrEmpty()){
            throw WebApplicationException(
                    Response.status(HttpServletResponse.SC_NO_CONTENT)
                            .entity("Agent JSON is empty").build())
        }
        val actor: Actor = gson.fromJson(agentDetailsJson, Actor::class.java)
        log.debug(">> Actor: $actor")
        val actorDetails: Actor? = service.getAgentDetails(actor)
        if (actorDetails != null) {
            agentEventCalled(request, context, actor, "POST")
            return Response.status(HttpServletResponse.SC_OK).entity(actorDetails).build()
        } else {
            log.debug(">>> No Agent found")
            throw WebApplicationException(
                    Response.status(HttpServletResponse.SC_NOT_FOUND).build())
        }
    }

    fun agentEventCalled(request: HttpServletRequest, context: SecurityContext,
                         actor: Actor, method: String) {
        val remoteIp = request.remoteAddr
        var userName:String
        if(context.userPrincipal is UsernamePasswordAuthenticationToken){
            val userPassAuthToken = context.userPrincipal as UsernamePasswordAuthenticationToken
            userName = (userPassAuthToken.principal as UserAccount).name
        } else {
            userName = context.userPrincipal.name
        }
        val currentTime = Timestamp(Calendar.getInstance().time.time).toString()
        val attrsMap = InverseFunctionalIdentifierHelper.getAvailableAttrsFromActor(actor)
        val ids: MutableList<String> = mutableListOf()
        for ((key, value) in attrsMap) {
            ids.add(key +"="+ value)
        }
        val xapiData = XapiEventData("Agent", ids)
        val event = XapiEvent(userName, currentTime, xapiData , method, remoteIp)
        eventPublisher.publishEvent(event)
    }
}
