package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.models.Activity

interface ActivitiesService {

    fun getActivity(id: String) : Activity?
    fun getAll() : List<Activity>?
    fun getCount() : Long
    fun exists(activity: Activity) : Boolean

}