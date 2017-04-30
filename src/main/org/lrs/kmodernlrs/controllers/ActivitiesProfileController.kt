package org.lrs.kmodernlrs.controllers

import org.lrs.kmodernlrs.ApiEndpoints
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.Path

@RestController
@Path(ApiEndpoints.ACTIVITIES_PROFILE_ENDPOINT)
open class ActivitiesProfileController {

    val log: Logger = LoggerFactory.getLogger(ActivitiesProfileController::class.java)

    // TODO:

}