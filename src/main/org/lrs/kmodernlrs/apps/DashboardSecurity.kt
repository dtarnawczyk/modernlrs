package org.lrs.kmodernlrs.apps

//import org.springframework.context.ApplicationContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
@Order(1)
open class DashboardSecurity : WebSecurityConfigurerAdapter() {

    @Value("&{dashboard.user.name}")
    lateinit var user: String

    @Value("&{dashboard.user.password}")
    lateinit var password: String

    override fun configure(http: WebSecurity) {
        http.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/dist/**")
                .antMatchers("/img/**")
    }

    override fun configure(http: HttpSecurity) {
//        http
//                .authorizeRequests()
//                .antMatchers("/",
//                        "/activityLogView",
//                        "/statementsView",
//                        "/verbsView",
//                        "/agentsView",
//                        "/activitiesView",
//                        "/reportsView",
//                        "/usersView").permitAll()
        http.authorizeRequests()
                .antMatchers("/",
                        "/activityLogView",
                        "/statementsView",
                        "/verbsView",
                        "/agentsView",
                        "/activitiesView",
                        "/reportsView",
                        "/usersView").authenticated()
                .and()
                .formLogin()
                    .defaultSuccessUrl("/")
                    .loginPage("/login").permitAll()
                    .and()
                .logout().permitAll()
                .and().csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
                .withUser(user).password(password).roles("USER")
    }
}