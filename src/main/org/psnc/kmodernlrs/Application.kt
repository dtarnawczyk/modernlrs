package org.psnc.kmodernlrs

import org.springframework.boot.SpringApplication
//import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration(exclude = arrayOf(JacksonAutoConfiguration::class,
		HibernateJpaAutoConfiguration::class))
@ComponentScan(basePackages=arrayOf("org.psnc.kmodernlrs"))
open class Application {
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
}