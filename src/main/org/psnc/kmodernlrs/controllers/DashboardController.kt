package org.psnc.kmodernlrs.controllers

import org.psnc.kmodernlrs.event.XapiEvent
import org.psnc.kmodernlrs.models.Activity
import org.psnc.kmodernlrs.models.Actor
import org.psnc.kmodernlrs.models.Statement
import org.psnc.kmodernlrs.security.UserAccount
import org.psnc.kmodernlrs.security.UserAccountProvider
import org.psnc.kmodernlrs.services.*
import org.springframework.stereotype.Controller
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest

@Controller
open class DashboardController {

    val log: Logger = LoggerFactory.getLogger(DashboardController::class.java)

    @Autowired lateinit var activityLogService: EventService
    @Autowired lateinit var statementService: StatementService
    @Autowired lateinit var agentsService: AgentsService
    @Autowired lateinit var activitiesService: ActivitiesService
    @Autowired lateinit var userAccountsProvider: UserAccountProvider

    @RequestMapping(path = arrayOf("/"), method = arrayOf(RequestMethod.GET))
    fun index(httpRequest: HttpServletRequest, model: Model): String {
        return "index"
    }

//    @RequestMapping(value = "/{[path:[^\\.]*}")
//    fun forwardIndex(httpRequest: HttpServletRequest): ModelAndView {
//        return ModelAndView("forward:/")
//    }

    @RequestMapping(path = arrayOf("/activityLogView",
            "/statementsView", "/agentsView", "/activitiesView",
            "/reportsView", "/usersView", "/createUser"), method = arrayOf(RequestMethod.GET))
    fun activityLogView(httpRequest: HttpServletRequest) : ModelAndView {
        return ModelAndView("forward:/")
    }

    @RequestMapping(path = arrayOf("/activityLogInit"), method = arrayOf(RequestMethod.GET))
    fun activityLogInit(httpRequest: HttpServletRequest) : ResponseEntity<List<XapiEvent>> {
        log.debug(">>>>> activity log view init")
        var allEvents: List<XapiEvent>? = activityLogService.getAllEvents()
        if(allEvents == null || allEvents?.isEmpty()){
            return ResponseEntity<List<XapiEvent>>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<XapiEvent>>(allEvents, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/statementsInit"), method = arrayOf(RequestMethod.GET))
    fun statementsViewInit(httpRequest: HttpServletRequest) : ResponseEntity<List<Statement>> {
        log.debug(">>>>> statements view init")
        var allStatements: List<Statement>? = statementService.getAll()
        if (allStatements == null || allStatements?.isEmpty()) {
            return ResponseEntity<List<Statement>>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Statement>>(allStatements, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/agentsInit"), method = arrayOf(RequestMethod.GET))
    fun agentsViewInit(httpRequest: HttpServletRequest) : ResponseEntity<List<Actor>> {
        log.debug(">>>>> agents view init")
        var allAgents: List<Actor>? = agentsService.getAll()
        if (allAgents == null || allAgents?.isEmpty()) {
            return ResponseEntity<List<Actor>>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Actor>>(allAgents, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/activitiesInit"), method = arrayOf(RequestMethod.GET))
    fun activitiesViewInit(httpRequest: HttpServletRequest) : ResponseEntity<List<Activity>> {
        log.debug(">>>>> activities view init")
        var allActivity: List<Activity>? = activitiesService.getAll()
        if (allActivity == null || allActivity?.isEmpty()) {
            return ResponseEntity<List<Activity>>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Activity>>(allActivity, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/reportsInit"), method = arrayOf(RequestMethod.GET))
    fun reportsViewInit(httpRequest: HttpServletRequest) : ModelAndView {
        return ModelAndView("forward:/")
    }

    @RequestMapping(path = arrayOf("/usersInit"), method = arrayOf(RequestMethod.GET))
    fun usersViewInit(httpRequest: HttpServletRequest) : ResponseEntity<List<UserAccount>> {
        log.debug(">>>>> users view init")
        var allUsers: List<UserAccount>? = userAccountsProvider.getAllUserAccounts()
        if (allUsers == null || allUsers?.isEmpty()) {
            return ResponseEntity<List<UserAccount>>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<UserAccount>>(allUsers, HttpStatus.OK)
    }
}