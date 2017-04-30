package org.lrs.kmodernlrs.controllers

//import javax.ws.rs.Produces
import com.google.gson.Gson
import org.lrs.kmodernlrs.ApiEndpoints
import org.lrs.kmodernlrs.event.XapiEvent
import org.lrs.kmodernlrs.event.XapiEventData
import org.lrs.kmodernlrs.gson.GsonFactoryProvider
import org.lrs.kmodernlrs.models.Activity
import org.lrs.kmodernlrs.security.UserAccount
import org.lrs.kmodernlrs.services.ActivitiesService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

data class ActivityId(var activityId: String)

@RestController
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Cacheable("statements")
    fun getActivity(@Context request: HttpServletRequest, @Context context: SecurityContext,
                    activityIdStr: String) : Response {
        if(activityIdStr.isNullOrEmpty()){
            throw WebApplicationException(
                    Response.status(HttpServletResponse.SC_NO_CONTENT)
                            .entity("Activity Id JSON is empty").build())
        }
        val activityId: String = getActivityIdFromString(activityIdStr)
        val activity = service.getActivity(activityId)
        if (activity != null) {
            activityEventCalled(request, context, activity, "POST")
            return Response.status(HttpServletResponse.SC_OK).entity(gson.toJson(activity)).build()
        } else {
            log.debug(">>> No Activity found")
            throw WebApplicationException(
                    Response.status(HttpServletResponse.SC_NOT_FOUND).build())
        }
    }

    fun getActivityIdFromString(activityIdJson: String): String {
        val activityId: ActivityId = gson.fromJson(activityIdJson, ActivityId::class.java)
        return activityId.activityId
    }

    fun activityEventCalled(request: HttpServletRequest, context: SecurityContext,
                            activity: Activity, method: String) {
        val remoteIp = request.remoteAddr
        var userName: String
        if(context.userPrincipal is UsernamePasswordAuthenticationToken){
            val userPassAuthToken = context.userPrincipal as UsernamePasswordAuthenticationToken
            userName = (userPassAuthToken.principal as UserAccount).name
        } else {
            userName = context.userPrincipal.name
        }
        val currentTime = Timestamp(Calendar.getInstance().time.time).toString()
        val xapiData = XapiEventData("Activity", activity.id)
        val event = XapiEvent(userName, currentTime, xapiData , method, remoteIp)
        eventPublisher.publishEvent(event)
    }
}