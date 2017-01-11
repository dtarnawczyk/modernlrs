package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Controller
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.RequestMethod



@Controller
open class DashboardController {

    val log: Logger = LoggerFactory.getLogger(DashboardController::class.java)

    @RequestMapping(path = arrayOf("/"), method = arrayOf(RequestMethod.GET))
    fun index(): String {
        log.debug(">>>> DashboardController index <<<")
        return "index"
    }

//    @RequestMapping("/activityLogView",
//            "/statementsView",
//            "/verbsView",
//            "/agentsView",
//            "/activitiesView",
//            "/reportsView",
//            "/usersView")
    @RequestMapping(value = "/{[path:[^\\.]*}")
    fun forwardIndex(): ModelAndView {
        val model: ModelMap = ModelMap()
        log.debug(">>> Forwarding to index <<<")
        return ModelAndView("forward:/", model)
    }
}