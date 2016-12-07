package org.psnc.kmodernlrs.filters

import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.psnc.kmodernlrs.security.UserAccountAuth
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

@Component("BasicAuthFilter")
class BasicAuthFilter : OncePerRequestFilter() {
	
	val log = LoggerFactory.getLogger(BasicAuthFilter::class.java)
	
	override fun doFilterInternal(request: HttpServletRequest,
								  response: HttpServletResponse, chain: FilterChain) {
		var authHeader: String? = request.getHeader("Authorization")
		
		log.debug(String.format(">>> Basic auth - Authorization Header: %s", authHeader))
		
		if(authHeader != null){
			if (StringUtils.isNotBlank(authHeader)) {
				var auth: Authentication = UserAccountAuth(authHeader)
				SecurityContextHolder.getContext().setAuthentication(auth)
			} else if ("OPTIONS".equals(request.getMethod())) {
				log.warn("OPTIONS request - returning no content");
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			} else {
				response.sendError(401, "Missing Authorization Header")
				throw SecurityException()
			}
		}
		
        chain.doFilter(request, response)
	}
}