package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Controller
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping

@Controller
open class DashboardController {

    val log: Logger = LoggerFactory.getLogger(DashboardController::class.java)

    @RequestMapping("/")
    fun index(): String {
        log.debug(">>>> DashboardController index <<<")
        return "index"
    }

}