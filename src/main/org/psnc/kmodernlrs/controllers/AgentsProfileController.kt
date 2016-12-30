package org.psnc.kmodernlrs.controllers

import org.psnc.kmodernlrs.ApiEndpoints
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Component
@Path(ApiEndpoints.AGENTS_PROFILE_ENDPOINT)
open class AgentsProfileController {

    val log = LoggerFactory.getLogger(AgentsProfileController::class.java)

    // TODO:

}