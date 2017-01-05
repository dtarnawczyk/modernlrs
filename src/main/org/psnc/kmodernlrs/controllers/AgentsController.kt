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
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import org.psnc.kmodernlrs.models.Actor

import org.psnc.kmodernlrs.gson.GsonFactoryProvider
import org.psnc.kmodernlrs.services.AgentsService

@Component
@Path(ApiEndpoints.AGENTS_ENDPOINT)
open class AgentsController {

    val log: Logger = LoggerFactory.getLogger(AgentsController::class.java)

    @Autowired lateinit var service: AgentsService

    lateinit var gson:Gson

    @Autowired
    fun setGsonProvider(gsonFactory: GsonFactoryProvider){
        gson = gsonFactory.gsonFactory()
    }

    @POST
    @Consumes(JSON_TYPE)
    @Produces(JSON_TYPE)
    fun getAgents(json: String) : String {
        val actor: Actor = gson.fromJson(json, Actor::class.java)
        log.debug(">> Actor: "+ actor)
        val actorDetails: Actor? = service.getAgentDetails(actor)
        return gson.toJson(actorDetails)
    }

}
