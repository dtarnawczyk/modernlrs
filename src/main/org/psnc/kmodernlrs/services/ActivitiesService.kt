package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.models.Activity

interface ActivitiesService {

    fun getActivity(id: String) : Activity?
    fun getAll() : List<Activity>?
    fun get10Activities(from: Int) : List<Activity>?
    fun get20Activities(from: Int) : List<Activity>?
    fun get50Activities(from: Int) : List<Activity>?
    fun getActivitiesLimitFrom(limit: Int, from: Int) : List<Activity>?
    fun getCount() : Long
    fun exists(activity: Activity) : Boolean

}