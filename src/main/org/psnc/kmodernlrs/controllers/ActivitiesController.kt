package org.psnc.kmodernlrs.controllers

import org.psnc.kmodernlrs.ApiEndpoints
import javax.ws.rs.Path
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.Produces

@Component
@Path(ApiEndpoints.ACTIVITIES_ENDPOINT)
open class ActivitiesController {

    val log = LoggerFactory.getLogger(ActivitiesController::class.java)

    // TODO:

}