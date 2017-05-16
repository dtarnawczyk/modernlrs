package org.lrs.kmodernlrs.controllers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.Path

@RestController
@Path(ApiEndpoints.ACTIVITIES_STATE_ENDPOINT)
open class ActivitiesStateController {

    val log: Logger = LoggerFactory.getLogger(ActivitiesStateController::class.java)

    // TODO:

}
