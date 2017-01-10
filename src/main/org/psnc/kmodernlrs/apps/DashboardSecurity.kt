package org.psnc.kmodernlrs.apps

import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
//import org.springframework.context.ApplicationContext
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity

@Configuration
@EnableWebSecurity
@Order(1)
open class DashboardSecurity : WebSecurityConfigurerAdapter() {

    override fun configure(http: WebSecurity) {
        http.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/dist/**")
                .antMatchers("/img/**")
    }

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers("/",
                        "/activityLogView",
                        "/statementsView",
                        "/verbsView",
                        "/agentsView",
                        "/activitiesView",
                        "/reportsView",
                        "/usersView").permitAll()
                .and().csrf().disable()
    }

//        override fun configure(auth: AuthenticationManagerBuilder) {
//            auth.authenticationProvider(authProvider)
//        }
}