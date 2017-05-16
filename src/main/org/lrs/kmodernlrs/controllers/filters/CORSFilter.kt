package org.lrs.kmodernlrs.controllers.filters

import org.apache.log4j.Logger
import org.lrs.kmodernlrs.Constants
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CORSFilter : OncePerRequestFilter() {

    val log: Logger = Logger.getLogger(CORSFilter::class.java)

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse, filterChain: FilterChain) {
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, " +
                Constants.XAPI_VERSION_HEADER)
        filterChain.doFilter(request, response)
    }
}