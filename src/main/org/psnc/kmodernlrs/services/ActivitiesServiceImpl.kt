package org.psnc.kmodernlrs.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.psnc.kmodernlrs.models.Activity
import org.psnc.kmodernlrs.repository.RepositoryCustomImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
open class ActivitiesServiceImpl : ActivitiesService {

    val log: Logger = LoggerFactory.getLogger(ActivitiesServiceImpl::class.java)

    @Autowired
    lateinit var repo: RepositoryCustomImpl

    override fun getActivity(id: String) : Activity? = repo.findById(id, Activity::class.java) as Activity?

    override fun getAll() : List<Activity>? {
        val activities: List<Activity>? = repo.findAll(Activity::class.java)?.filterIsInstance<Activity>()
        return activities
    }

    override fun getCount() : Long = repo.getCount(Activity::class.java)

    override fun exists(activity: Activity) : Boolean = repo.exists(activity.id, Activity::class.java)
}