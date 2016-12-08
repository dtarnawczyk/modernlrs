package org.psnc.kmodernlrs.filters

import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Primary
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

@ConditionalOnProperty(name=arrayOf("auth.type"), havingValue="oauth")
@Component
@Primary
class OAuthFilter : OncePerRequestFilter() {
	
	val log = LoggerFactory.getLogger(OAuthFilter::class.java)
	
	override fun doFilterInternal(request: HttpServletRequest,
								  response: HttpServletResponse, chain: FilterChain) {
		var authHeader: String = request.getHeader("Authorization")
		
		log.debug(String.format(">>> OAuth - Authorization Header: %s", authHeader))
		// TODO: implement this
		// forward the request
        chain.doFilter(request, response)
	}
}