package org.psnc.kmodernlrs.controllers

import org.psnc.kmodernlrs.ApiEndpoints
import javax.ws.rs.Path
import org.springframework.stereotype.Component
import com.google.gson.Gson
import org.psnc.kmodernlrs.gson.GsonFactoryProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.POST
//import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import org.psnc.kmodernlrs.services.ActivitiesService
import org.springframework.beans.factory.annotation.Autowired

data class ActivityId(var activityId: String) {}

@Component
@Path(ApiEndpoints.ACTIVITIES_ENDPOINT)
open class ActivitiesController {

    val log: Logger = LoggerFactory.getLogger(ActivitiesController::class.java)

    @Autowired lateinit var service: ActivitiesService

    lateinit var gson:Gson

    @Autowired
    fun setGsonProvider(gsonFactory: GsonFactoryProvider){
        gson = gsonFactory.gsonFactory()
    }

    @POST
    @Consumes(JSON_TYPE)
    fun getActivity(activityId: String) : String {
        val activityIdStr: String = getActivityIdFromString(activityId)
        val activity = service.getActivity(activityIdStr)
        if(activity != null) {
            val activityStr: String = gson.toJson(activity)
            return activityStr
        } else {
            return "No activity found"
        }
    }

    fun getActivityIdFromString(activityIdJson: String) : String {
        val activityId: ActivityId = gson.fromJson(activityIdJson, ActivityId::class.java)
        return activityId.activityId
    }
}