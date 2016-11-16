package org.psnc.kmodernlrs.apps

import org.glassfish.jersey.server.ResourceConfig
import javax.ws.rs.ApplicationPath
import org.glassfish.jersey.servlet.ServletProperties
import org.psnc.kmodernlrs.controllers.StatementsController
import org.springframework.stereotype.Component

@Component
@ApplicationPath("/")
open class JerseyConfig() : ResourceConfig() {
	init {
		register(StatementsController())
		
		property(ServletProperties.FILTER_FORWARD_ON_404, true);
	}	
}