package org.psnc.kmodernlrs.controllers

import org.psnc.kmodernlrs.ApiEndpoints
import javax.ws.rs.Path
import org.springframework.stereotype.Component
import com.google.gson.Gson
import org.psnc.kmodernlrs.event.XapiEvent
import org.psnc.kmodernlrs.event.XapiEventData
import org.psnc.kmodernlrs.gson.GsonFactoryProvider
import org.psnc.kmodernlrs.models.Activity
import org.psnc.kmodernlrs.security.UserAccount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.POST
//import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import org.psnc.kmodernlrs.services.ActivitiesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext

data class ActivityId(var activityId: String) {}

@Component
@Path(ApiEndpoints.ACTIVITIES_ENDPOINT)
open class ActivitiesController {

    val log: Logger = LoggerFactory.getLogger(ActivitiesController::class.java)

    @Autowired lateinit var service: ActivitiesService
    @Autowired lateinit var eventPublisher: ApplicationEventPublisher

    lateinit var gson: Gson

    @Autowired
    fun setGsonProvider(gsonFactory: GsonFactoryProvider) {
        gson = gsonFactory.gsonFactory()
    }

    @POST
    @Consumes(JSON_TYPE)
    @Cacheable("statements")
    fun getActivity(@Context request: HttpServletRequest, @Context context: SecurityContext,
                    activityIdStr: String): String {
        val activityId: String = getActivityIdFromString(activityIdStr)
        val activity = service.getActivity(activityId)
        if (activity != null) {
            activityEventCalled(request, context, activity, "POST")
            val activityStr: String = gson.toJson(activity)
            return activityStr
        } else {
            return "No activity found"
        }
    }

    fun getActivityIdFromString(activityIdJson: String): String {
        val activityId: ActivityId = gson.fromJson(activityIdJson, ActivityId::class.java)
        return activityId.activityId
    }

    fun activityEventCalled(request: HttpServletRequest, context: SecurityContext,
                            activity: Activity, method: String) {
        val remoteIp = request.remoteAddr
        var userName = ""
        if(context.userPrincipal is UsernamePasswordAuthenticationToken){
            var userPassAuthToken = context.userPrincipal as UsernamePasswordAuthenticationToken
            userName = (userPassAuthToken.principal as UserAccount).name
        } else {
            userName = context.userPrincipal.name
        }
        val currentTime = Timestamp(Calendar.getInstance().getTime().getTime()).toString()
        var xapiData = XapiEventData("Activity", activity.id)
        val event = XapiEvent(userName, currentTime, xapiData , method, remoteIp)
        eventPublisher.publishEvent(event)
    }
}