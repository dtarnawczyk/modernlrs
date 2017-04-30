package org.lrs.kmodernlrs.services

//import org.lrs.kmodernlrs.repository.RepositoryCustomImpl
import org.lrs.kmodernlrs.models.Activity
import org.lrs.kmodernlrs.models.Entity
import org.lrs.kmodernlrs.repository.RepositoryCustom
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class ActivitiesServiceImpl : ActivitiesService {

    val log: Logger = LoggerFactory.getLogger(ActivitiesServiceImpl::class.java)

    @Autowired
    lateinit var repo: RepositoryCustom<Entity>

    override fun getActivity(id: String) : Activity? = repo.findById(id, Activity::class.java) as Activity?

    override fun getAll() : List<Activity>? {
        return repo.findAll(Activity::class.java)?.filterIsInstance<Activity>()
    }

    override fun get10Activities(from: Int) : List<Activity>? {
       return repo.find10(Activity::class.java, from)?.filterIsInstance<Activity>()
    }

    override fun get20Activities(from: Int) : List<Activity>? {
        return repo.find20(Activity::class.java, from)?.filterIsInstance<Activity>()
    }

    override fun get50Activities(from: Int) : List<Activity>? {
        return repo.find50(Activity::class.java, from)?.filterIsInstance<Activity>()
    }

    override fun getActivitiesLimitFrom(limit: Int, from: Int) : List<Activity>? {
        return repo.findAllLimitSkip(Activity::class.java, limit, from)?.filterIsInstance<Activity>()
    }

    override fun getCount() : Long = repo.getCount(Activity::class.java)

    override fun exists(activity: Activity) : Boolean = repo.exists(activity.id, Activity::class.java)
}