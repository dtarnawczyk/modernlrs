package org.lrs.kmodernlrs.apps

import org.lrs.kmodernlrs.ApiEndpoints
import org.lrs.kmodernlrs.filters.HeaderFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.header.HeaderWriterFilter

@Configuration
@EnableWebSecurity
@Order(0)
open class XapiSecurity : WebSecurityConfigurerAdapter() {

    //	@Value("&{auth.type}")
//	lateinit var authType:String

    @Autowired
    lateinit var authProvider: AuthenticationProvider

//    @Autowired
//    lateinit var filter: OncePerRequestFilter

    @Autowired
    lateinit var headerFilter: HeaderFilter

    @Value("&{management.security.roles}")
    lateinit var adminRole: String

    override fun configure(http: HttpSecurity) {
        http.antMatcher("/v1/xAPI/**")
        .addFilterBefore(headerFilter, HeaderWriterFilter::class.java)
//      .addFilterBefore(filter, BasicAuthenticationFilter::class.java)
        .authorizeRequests()
        .antMatchers("/v1/xAPI/about").permitAll()
        .antMatchers("/v1/xAPI/**").hasAnyRole("USER", adminRole)
        .antMatchers(*actuatorEndpoints()).hasRole(adminRole)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .httpBasic()
        .and()
//        .anonymous().disable()
        .csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider)
    }

    fun actuatorEndpoints() : Array<String> {
        return arrayOf(ApiEndpoints.AUTOCONFIG_ENDPOINT, ApiEndpoints.BEANS_ENDPOINT, ApiEndpoints.CONFIGPROPS_ENDPOINT,
                ApiEndpoints.ENV_ENDPOINT, ApiEndpoints.MAPPINGS_ENDPOINT,
                ApiEndpoints.METRICS_ENDPOINT, ApiEndpoints.SHUTDOWN_ENDPOINT)
    }
}