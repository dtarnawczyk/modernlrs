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
import org.springframework.security.web.header.HeaderWriterFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
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
	
	@Autowired
	lateinit var headerFilter: HeaderFilter
	
	@Value("&{management.security.roles}")
    lateinit var adminRole: String
	
	private var statementPath: String = "/v1/xAPI/statements"
	
	override fun configure(http: HttpSecurity) {	
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.antMatcher(statementPath+"**").addFilterBefore(headerFilter, HeaderWriterFilter::class.java)
			.addFilterAfter(filter, BasicAuthenticationFilter::class.java)
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
	
//	@Bean
//    open fun  filterRegistrationBean() : FilterRegistrationBean {  
//        var registrationBean: FilterRegistrationBean = FilterRegistrationBean()     
//        registrationBean.setFilter(headerFilter)    
//        var urls: MutableList<String> = mutableListOf<String>()
//		urls.add(statementPath)
//		registrationBean.setUrlPatterns(urls)
//		registrationBean.setOrder(1)
//		return registrationBean
//    }
//	
//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("http://domain1.com");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(0);
//		return bean;
//	}
	
	fun actuatorEndpoints() : List<String> {
        return listOf(ApiEndpoints.AUTOCONFIG_ENDPOINT, ApiEndpoints.BEANS_ENDPOINT, ApiEndpoints.CONFIGPROPS_ENDPOINT,
                ApiEndpoints.ENV_ENDPOINT, ApiEndpoints.MAPPINGS_ENDPOINT,
                ApiEndpoints.METRICS_ENDPOINT, ApiEndpoints.SHUTDOWN_ENDPOINT)
    }
}