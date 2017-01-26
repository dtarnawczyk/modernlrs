package org.lrs.kmodernlrs.filters

import org.apache.commons.lang3.StringUtils
import org.lrs.kmodernlrs.Constants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HeaderFilter : OncePerRequestFilter() {
	
	val log: Logger = LoggerFactory.getLogger(HeaderFilter::class.java)
	
	@Value("&{xapi.version}")
	lateinit var version: String
	
	override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse, chain: FilterChain) {
		
		log.debug(">>> Header filter <<")
		val allowedRequestHeader: String? = request.getHeader("Access-Control-Request-Headers")
		var responseHeader:String = Constants.XAPI_VERSION_HEADER
		
		if (StringUtils.isNotBlank(allowedRequestHeader)) {
			if (StringUtils.contains(allowedRequestHeader, responseHeader.toLowerCase()))
				responseHeader = responseHeader.toLowerCase()
		}
		response.addHeader(responseHeader, version)
		
		chain.doFilter(request, response)
	
	}
}