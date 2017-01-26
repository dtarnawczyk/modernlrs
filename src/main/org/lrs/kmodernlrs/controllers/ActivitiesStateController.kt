package org.lrs.kmodernlrs.controllers

import org.lrs.kmodernlrs.ApiEndpoints
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.ws.rs.Path

@Component
@Path(ApiEndpoints.ACTIVITIES_STATE_ENDPOINT)
open class ActivitiesStateController {

    val log: Logger = LoggerFactory.getLogger(ActivitiesStateController::class.java)

    // TODO:

}
