package org.lrs.kmodernlrs

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@EnableCaching
//@EnableAutoConfiguration(exclude = arrayOf(JacksonAutoConfiguration::class,
//		HibernateJpaAutoConfiguration::class))
@EnableAutoConfiguration(exclude = arrayOf(HibernateJpaAutoConfiguration::class))
@ComponentScan(basePackages=arrayOf("org.lrs.kmodernlrs"))
@SpringBootApplication
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

}