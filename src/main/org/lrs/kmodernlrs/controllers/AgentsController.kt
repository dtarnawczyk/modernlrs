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
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext

@Component
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
    @Consumes(JSON_TYPE)
    @Produces(JSON_TYPE)
    @Cacheable("statements")
    fun getAgents(@Context request: HttpServletRequest, @Context context: SecurityContext,
                  json: String) : ResponseEntity<String> {
        val actor: Actor = gson.fromJson(json, Actor::class.java)
        log.debug(">> Actor: $actor")
        val actorDetails: Actor? = service.getAgentDetails(actor)
        if (actorDetails != null) {
            agentEventCalled(request, context, actor, "POST")
            return ResponseEntity(gson.toJson(actorDetails), HttpStatus.OK)
        } else {
            return ResponseEntity("No activity found", HttpStatus.NOT_FOUND)
        }
    }

    fun agentEventCalled(request: HttpServletRequest, context: SecurityContext,
                         actor: Actor, method: String) {
        val remoteIp = request.remoteAddr
        var userName = ""
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
