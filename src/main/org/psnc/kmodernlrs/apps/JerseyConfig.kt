package org.psnc.kmodernlrs.apps

import org.glassfish.jersey.server.ResourceConfig
import javax.ws.rs.ApplicationPath
import org.glassfish.jersey.servlet.ServletProperties
import org.psnc.kmodernlrs.controllers.*
import org.springframework.stereotype.Component
import org.psnc.kmodernlrs.gson.GsonMessageHandler
import org.psnc.kmodernlrs.ApiEndpoints

@Component
@ApplicationPath(ApiEndpoints.API_PATH)
open class JerseyConfig : ResourceConfig {
	
	constructor() {
		register(GsonMessageHandler())
		register(AboutController())
		register(AgentsController())
		register(StatementsController())
		register(ActivitiesController())
		
		property(ServletProperties.FILTER_FORWARD_ON_404, true);
	}	
}