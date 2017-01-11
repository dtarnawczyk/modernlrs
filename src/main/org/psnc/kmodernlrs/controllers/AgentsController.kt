package org.psnc.kmodernlrs.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.psnc.kmodernlrs.ApiEndpoints
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import com.google.gson.Gson
import org.psnc.kmodernlrs.event.XapiEvent
import org.psnc.kmodernlrs.event.XapiEventData
import org.psnc.kmodernlrs.models.Actor
import org.psnc.kmodernlrs.gson.GsonFactoryProvider
import org.psnc.kmodernlrs.services.AgentsService
import org.psnc.kmodernlrs.util.InverseFunctionalIdentifierHelper
import org.springframework.context.ApplicationEventPublisher
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext

@Component
@Path(ApiEndpoints.AGENTS_ENDPOINT)
open class AgentsController {

    val log: Logger = LoggerFactory.getLogger(AgentsController::class.java)

    @Autowired lateinit var service: AgentsService
    @Autowired lateinit var eventPublisher: ApplicationEventPublisher

    lateinit var gson:Gson

    @Autowired
    fun setGsonProvider(gsonFactory: GsonFactoryProvider){
        gson = gsonFactory.gsonFactory()
    }

    @POST
    @Consumes(JSON_TYPE)
    @Produces(JSON_TYPE)
    fun getAgents(@Context request: HttpServletRequest, @Context context: SecurityContext,
                  json: String) : String {
        val actor: Actor = gson.fromJson(json, Actor::class.java)
        log.debug(">> Actor: "+ actor)
        val actorDetails: Actor? = service.getAgentDetails(actor)
        agentEventCalled(request, context, actor, "POST")
        return gson.toJson(actorDetails)
    }

    fun agentEventCalled(request: HttpServletRequest, context: SecurityContext,
                         actor: Actor, method: String) {
        val remoteIp = request.remoteAddr
        val userName = context.userPrincipal.name
        val currentTime = Timestamp(Calendar.getInstance().getTime().getTime()).toString()
        val attrsMap = InverseFunctionalIdentifierHelper.getAvailableAttrsFromActor(actor)
        var ids: MutableList<String> = mutableListOf()
        for (entry in attrsMap) {
            ids.add(entry.key+"="+entry.value)
        }
        var xapiData = XapiEventData("agent", ids)
        val event = XapiEvent(userName, currentTime, xapiData , method, remoteIp)
        eventPublisher.publishEvent(event)
    }
}
