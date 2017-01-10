package org.psnc.kmodernlrs.apps

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry

@Configuration
open class MvcConfig : WebMvcConfigurerAdapter() {

//    override fun addViewControllers(registry: ViewControllerRegistry) {
//        registry.addViewController("/").setViewName("index")
//    }

}