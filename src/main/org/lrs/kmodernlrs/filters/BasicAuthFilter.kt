package org.lrs.kmodernlrs.filters

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ConditionalOnProperty(name=arrayOf("auth.type"), havingValue="basic")
@Component
@Primary
class BasicAuthFilter : OncePerRequestFilter() {
	
	val log: Logger = LoggerFactory.getLogger(BasicAuthFilter::class.java)
	
	override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse, chain: FilterChain) {

		//  Uncomment this to filter all requests !!!
//		val authHeader: String? = request.getHeader("Authorization")
//		log.debug(String.format(">>> Basic auth - Authorization Header: %s", authHeader))
//		if(authHeader != null){
//			if (StringUtils.isNotBlank(authHeader)) {
//				val auth: Authentication = UserAccountAuth(authHeader)
//				SecurityContextHolder.getContext().authentication = auth
//			} else if ("OPTIONS" == request.method) {
//				log.warn("OPTIONS request - returning no content")
//				response.status = HttpServletResponse.SC_NO_CONTENT
//			} else {
//				missingHeader(response)
//			}
//		} else {
//			missingHeader(response)
//		}
		
        chain.doFilter(request, response)
	}
	
	fun missingHeader(response: HttpServletResponse) {
		response.sendError(401, "Missing Authorization Header")
	}
}