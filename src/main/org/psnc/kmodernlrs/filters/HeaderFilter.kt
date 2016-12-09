package org.psnc.kmodernlrs.filters

import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Primary
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.servlet.FilterChain
import javax.servlet.ServletException
import org.apache.commons.lang3.StringUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HeaderFilter : OncePerRequestFilter() {
	
	val log = LoggerFactory.getLogger(HeaderFilter::class.java)
	
	@Value("&{xapi.version}")
	lateinit var version: String
	
	override fun doFilterInternal(request: HttpServletRequest,
								  response: HttpServletResponse, chain: FilterChain) {
		
		log.debug(">>> Header filter <<")
		var allowedRequestHeader: String? = request.getHeader("Access-Control-Request-Headers")
		var responseHeader:String = "X-Experience-API-Version"
		
		if (StringUtils.isNotBlank(allowedRequestHeader)) {
			if (StringUtils.contains(allowedRequestHeader, responseHeader.toLowerCase())) {
				responseHeader = responseHeader.toLowerCase();
			}
		}
		response.addHeader(responseHeader, version);
		
		chain.doFilter(request, response)
	
	}
}