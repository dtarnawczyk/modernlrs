package org.psnc.kmodernlrs

import org.springframework.boot.SpringApplication
//import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
//import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.scheduling.annotation.EnableAsync
//import org.springframework.scheduling.support.TaskUtils
//import org.springframework.core.task.SimpleAsyncTaskExecutor
//import org.springframework.context.event.SimpleApplicationEventMulticaster
//import org.springframework.context.event.ApplicationEventMulticaster

//@SpringBootApplication
@Configuration
//@EnableWebMvc
@EnableAutoConfiguration(exclude = arrayOf(JacksonAutoConfiguration::class,
		HibernateJpaAutoConfiguration::class))
@ComponentScan(basePackages=arrayOf("org.psnc.kmodernlrs"))
open class Application /*: SpringBootServletInitializer() */{
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
	
	@Bean
	open fun kotlinPropertyConfigurer() : PropertySourcesPlaceholderConfigurer {
	    val propertyConfigurer = PropertySourcesPlaceholderConfigurer()
	    propertyConfigurer.setPlaceholderPrefix("&{")
	    propertyConfigurer.setIgnoreUnresolvablePlaceholders(true)
	    return propertyConfigurer
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
}