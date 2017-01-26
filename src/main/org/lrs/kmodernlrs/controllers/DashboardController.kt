package org.lrs.kmodernlrs.controllers

import org.lrs.kmodernlrs.event.XapiEvent
import org.lrs.kmodernlrs.models.Activity
import org.lrs.kmodernlrs.models.Actor
import org.lrs.kmodernlrs.models.Statement
import org.lrs.kmodernlrs.security.UserAccount
import org.lrs.kmodernlrs.security.UserAccountProvider
import org.lrs.kmodernlrs.services.ActivitiesService
import org.lrs.kmodernlrs.services.AgentsService
import org.lrs.kmodernlrs.services.EventService
import org.lrs.kmodernlrs.services.StatementService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

data class PageRange(var pageSize:Int = 10, var currentPage:Int = 1)

@Controller
open class DashboardController {

    val log: Logger = LoggerFactory.getLogger(DashboardController::class.java)

    @Autowired lateinit var activityLogService: EventService
    @Autowired lateinit var statementService: StatementService
    @Autowired lateinit var agentsService: AgentsService
    @Autowired lateinit var activitiesService: ActivitiesService
    @Autowired lateinit var userAccountsProvider: UserAccountProvider

    @RequestMapping(path = arrayOf("/", "/activityLogView",
            "/statementsView", "/agentsView", "/activitiesView",
            "/reportsView", "/usersView", "/createUser"), method = arrayOf(RequestMethod.GET))
    fun index(httpRequest: HttpServletRequest): String {
        return "index"
    }

//    @RequestMapping(path = arrayOf("/activityLogView",
//            "/statementsView", "/agentsView", "/activitiesView",
//            "/reportsView", "/usersView", "/createUser"), method = arrayOf(RequestMethod.GET))
//    fun activityLogView(httpRequest: HttpServletRequest) : ModelAndView {
//        return ModelAndView("forward:/")
//    }

    @RequestMapping(value = "/login", method = arrayOf(RequestMethod.GET))
    fun login(model: Model, error: String?, logout: String?): String {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.")
        if (logout != null)
            model.addAttribute("logout", "You have been logged out successfully.")
        return "login"
    }

    @RequestMapping(path = arrayOf("/activityLogInit"), method = arrayOf(RequestMethod.GET))
    fun activityLogInit(httpRequest: HttpServletRequest) : ResponseEntity<List<XapiEvent>> {
        log.debug(">>>>> activity log view init")
        val allEvents: List<XapiEvent>? = activityLogService.get10Events(0)
        if(allEvents == null || allEvents.isEmpty()){
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<XapiEvent>>(allEvents, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/getEventsOnPageSize"), method = arrayOf(RequestMethod.POST))
    fun getEventsOnPageSize(@RequestBody pageSize: Int?) : ResponseEntity<List<XapiEvent>> {
        log.debug(">>>>> activity log view init with pageSize: $pageSize")
        var events: List<XapiEvent>? = null
        when(pageSize) {
            10 -> events = activityLogService.get10Events(0)
            20 -> events = activityLogService.get20Events(0)
            50 -> events = activityLogService.get50Events(0)
            else -> events = activityLogService.get10Events(0)
        }
        if(events == null || events.isEmpty()){
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<XapiEvent>>(events, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/getEventsOnNextPage"), method = arrayOf(RequestMethod.POST))
    fun getEventsOnNextPage(@RequestBody pageRange: PageRange) : ResponseEntity<List<XapiEvent>> {
        log.debug(">>>>> activity log view next page, currentPage: " +
                "${pageRange.currentPage}, pageSize: ")
        return getResponseEventsOnCurrentPageAndSize(pageRange)
    }

    @RequestMapping(path = arrayOf("/getEventsOnPrevPage"), method = arrayOf(RequestMethod.POST))
    fun getEventsOnPrevPage(@RequestBody pageRange: PageRange) : ResponseEntity<List<XapiEvent>> {
        log.debug(">>>>> activity log view prev page, currentPage:" +
                " ${pageRange.currentPage}, pageSize: ${pageRange.pageSize} ")
        return getResponseEventsOnCurrentPageAndSize(pageRange)
    }

    private fun getResponseEventsOnCurrentPageAndSize(pageRange: PageRange) : ResponseEntity<List<XapiEvent>> {
        val pageSize = pageRange.pageSize
        val currentPage = pageRange.currentPage
        val events: List<XapiEvent>?
                = activityLogService.getEventsLimitFrom(pageSize, currentPage*pageSize)
        if(events == null || events.isEmpty()){
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<XapiEvent>>(events, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/statementsInit"), method = arrayOf(RequestMethod.GET))
    fun statementsViewInit(httpRequest: HttpServletRequest) : ResponseEntity<List<Statement>> {
        log.debug(">>>>> statements view init")
        val allStatements: List<Statement>? = statementService.get10Statements(0)
        if (allStatements == null || allStatements.isEmpty()) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Statement>>(allStatements, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/getStatementsOnPageSize"), method = arrayOf(RequestMethod.POST))
    fun getStatementsOnPageSize(@RequestBody pageSize: Int?) : ResponseEntity<List<Statement>> {
        log.debug(">>> statements view init with pageSize: $pageSize" )
        var statements: List<Statement>? = null
        when(pageSize) {
            10 -> statements = statementService.get10Statements(0)
            20 -> statements = statementService.get20Statements(0)
            50 -> statements = statementService.get50Statements(0)
            else -> statements = statementService.get10Statements(0)
        }
        if(statements == null || statements.isEmpty()){
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Statement>>(statements, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/getStatementsOnNextPage"), method = arrayOf(RequestMethod.POST))
    fun getStatementsOnNextPage(@RequestBody pageRange: PageRange) : ResponseEntity<List<Statement>> {
        log.debug(">>>>> statements view next page, currentPage: " +
                "${pageRange.currentPage}, pageSize: ${pageRange.pageSize}")
        return getResponseStatementsOnCurrentPageAndSize(pageRange)
    }

    @RequestMapping(path = arrayOf("/getStatementsOnPrevPage"), method = arrayOf(RequestMethod.POST))
    fun getStatementsOnPrevPage(@RequestBody pageRange: PageRange) : ResponseEntity<List<Statement>> {
        log.debug(">>>>> statements view prev page, currentPage: " +
                "${pageRange.currentPage}, pageSize: ${pageRange.pageSize}")
        return getResponseStatementsOnCurrentPageAndSize(pageRange)
    }

    private fun getResponseStatementsOnCurrentPageAndSize(pageRange: PageRange) : ResponseEntity<List<Statement>> {
        val pageSize = pageRange.pageSize
        val currentPage = pageRange.currentPage
        val statements: List<Statement>?
                = statementService.getStatementsLimitFrom(pageSize, currentPage*pageSize)
        if(statements == null || statements.isEmpty()){
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Statement>>(statements, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/agentsInit"), method = arrayOf(RequestMethod.GET))
    fun agentsViewInit(httpRequest: HttpServletRequest) : ResponseEntity<List<Actor>> {
        log.debug(">>>>> agents view init")
        val allAgents: List<Actor>? = agentsService.get10Agents(0)
        if (allAgents == null || allAgents.isEmpty()) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Actor>>(allAgents, HttpStatus.OK)
    }


    @RequestMapping(path = arrayOf("/getAgentsOnPageSize"), method = arrayOf(RequestMethod.POST))
    fun getAgentsOnPageSize(@RequestBody pageSize: Int?) : ResponseEntity<List<Actor>> {
        log.debug(">>>>> agents view init with pageSize: $pageSize")
        var agents: List<Actor>? = null
        when(pageSize) {
            10 -> agents = agentsService.get10Agents(0)
            20 -> agents = agentsService.get20Agents(0)
            50 -> agents = agentsService.get50Agents(0)
            else -> agents = agentsService.get10Agents(0)
        }
        if(agents == null || agents.isEmpty()){
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Actor>>(agents, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/getAgentsOnNextPage"), method = arrayOf(RequestMethod.POST))
    fun getAgentsOnNextPage(@RequestBody pageRange: PageRange) : ResponseEntity<List<Actor>> {
        log.debug(">>>>> agents view next page, currentPage:" +
                " ${pageRange.currentPage}, pageSize: ${pageRange.pageSize}")
        return getResponseAgentsOnCurrentPageAndSize(pageRange)
    }

    @RequestMapping(path = arrayOf("/getAgentsOnPrevPage"), method = arrayOf(RequestMethod.POST))
    fun getAgentsOnPrevPage(@RequestBody pageRange: PageRange) : ResponseEntity<List<Actor>> {
        log.debug(">>>>> agents view prev page, currentPage:" +
                " ${pageRange.currentPage}, pageSize: ${pageRange.pageSize}")
        return getResponseAgentsOnCurrentPageAndSize(pageRange)
    }

    private fun getResponseAgentsOnCurrentPageAndSize(pageRange: PageRange) : ResponseEntity<List<Actor>> {
        val pageSize = pageRange.pageSize
        val currentPage = pageRange.currentPage
        val agents: List<Actor>?
                = agentsService.getAgentsLimitFrom(pageSize, currentPage*pageSize)
        if(agents == null || agents.isEmpty()){
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Actor>>(agents, HttpStatus.OK)
    }

    @RequestMapping(path = arrayOf("/activitiesInit"), method = arrayOf(RequestMethod.GET))
    fun activitiesViewInit(httpRequest: HttpServletRequest) : ResponseEntity<List<Activity>> {
        log.debug(">>>>> activities view init")
        val allActivity: List<Activity>? = activitiesService.getAll()
        if (allActivity == null || allActivity.isEmpty()) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
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
        val allUsers: List<UserAccount>? = userAccountsProvider.getAllUserAccounts()
        if (allUsers == null || allUsers.isEmpty()) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<UserAccount>>(allUsers, HttpStatus.OK)
    }
}