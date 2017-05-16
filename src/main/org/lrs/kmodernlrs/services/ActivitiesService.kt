package org.lrs.kmodernlrs.services

import org.lrs.kmodernlrs.domain.Activity

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