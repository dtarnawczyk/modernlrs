package org.psnc.kmodernlrs.controllers

import org.psnc.kmodernlrs.ApiEndpoints
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.Path
//import javax.ws.rs.Produces

@Component
@Path(ApiEndpoints.ABOUT_ENDPOINT)
open class AboutController {

    val log: Logger = LoggerFactory.getLogger(AboutController::class.java)

    @GET
    fun about() : String {
        // TODO:
        return "ModernLRS version 0.0.1"
    }

}
