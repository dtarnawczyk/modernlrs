package org.psnc.kmodernlrs.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
//import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.http.HttpServletResponse
import org.springframework.security.web.AuthenticationEntryPoint
import org.psnc.kmodernlrs.ApiEndpoints
import org.psnc.kmodernlrs.filters.*

@Configuration
@EnableWebSecurity
open class LRSSecurity : WebSecurityConfigurerAdapter() {
	
//	@Value("&{auth.type}")
//	lateinit var authType:String
	
	@Autowired
	lateinit var authProvider: AuthenticationProvider
	
	@Autowired
	lateinit var filter: OncePerRequestFilter
	
	@Value("&{management.security.roles}")
    lateinit var adminRole: String
	
	override fun configure(http: HttpSecurity) {	
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.antMatcher("/v1/xAPI/statements**").addFilterBefore(filter, BasicAuthenticationFilter::class.java)
			.authorizeRequests()
			.anyRequest().fullyAuthenticated()
//			.antMatchers(actuatorEndpoints()).hasRole(adminRole)
            .anyRequest().authenticated()
			.and()
			.anonymous().disable()
			.csrf().disable()
	}
	
	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.authenticationProvider(authProvider);
	}
	
	fun actuatorEndpoints() : List<String> {
        return listOf(ApiEndpoints.AUTOCONFIG_ENDPOINT, ApiEndpoints.BEANS_ENDPOINT, ApiEndpoints.CONFIGPROPS_ENDPOINT,
                ApiEndpoints.ENV_ENDPOINT, ApiEndpoints.MAPPINGS_ENDPOINT,
                ApiEndpoints.METRICS_ENDPOINT, ApiEndpoints.SHUTDOWN_ENDPOINT)
    }
}