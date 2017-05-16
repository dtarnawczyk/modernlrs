package org.lrs.kmodernlrs.controllers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.Path

@RestController
@Path(ApiEndpoints.AGENTS_PROFILE_ENDPOINT)
open class AgentsProfileController {

    val log: Logger = LoggerFactory.getLogger(AgentsProfileController::class.java)

    // TODO:

}