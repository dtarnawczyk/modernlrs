package org.lrs.kmodernlrs.apps

import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.servlet.ServletProperties
import org.lrs.kmodernlrs.controllers.AboutController
import org.lrs.kmodernlrs.controllers.ActivitiesController
import org.lrs.kmodernlrs.controllers.AgentsController
import org.lrs.kmodernlrs.controllers.StatementsController
import org.lrs.kmodernlrs.gson.GsonMessageHandler
import org.springframework.context.annotation.Configuration

@Configuration
open class JerseyConfig : ResourceConfig {
	
	constructor() {
		register(GsonMessageHandler())
		register(AboutController())
		register(AgentsController())
		register(StatementsController())
		register(ActivitiesController())
		
		property(ServletProperties.FILTER_FORWARD_ON_404, true)
	}	
}