package org.lrs.kmodernlrs.controllers.filters

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ConditionalOnProperty(name=arrayOf("auth.type"), havingValue="oauth")
@Component
@Primary
class OAuthFilter : OncePerRequestFilter() {
	
	val log: Logger = LoggerFactory.getLogger(OAuthFilter::class.java)
	
	override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse, chain: FilterChain) {
		val authHeader: String = request.getHeader("Authorization")
		
		log.debug(String.format(">>> OAuth - Authorization Header: %s", authHeader))
		// TODO: implement this if all request would need this ?
        chain.doFilter(request, response)
	}
}