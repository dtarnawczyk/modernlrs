package org.psnc.kmodernlrs.apps

import org.glassfish.jersey.server.ResourceConfig
import javax.ws.rs.ApplicationPath
import org.glassfish.jersey.servlet.ServletProperties
import org.psnc.kmodernlrs.controllers.StatementsController
import org.springframework.stereotype.Component
import org.psnc.kmodernlrs.gson.GsonMessageHandler
import org.psnc.kmodernlrs.serializers.*

@Component
@ApplicationPath("/v1/")
open class JerseyConfig : ResourceConfig {
	
	constructor() {
		register(GsonMessageHandler())
		register(StatementsController())
		
		property(ServletProperties.FILTER_FORWARD_ON_404, true);
	}	
}