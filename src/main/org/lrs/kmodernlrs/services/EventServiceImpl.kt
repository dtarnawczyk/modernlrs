package org.lrs.kmodernlrs.services

import org.lrs.kmodernlrs.event.XapiEvent
import org.lrs.kmodernlrs.models.Entity
import org.lrs.kmodernlrs.repository.RepositoryCustom
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class EventServiceImpl : EventService {

    val log: Logger = LoggerFactory.getLogger(AgentsServiceImpl::class.java)

    @Autowired
    lateinit var repo: RepositoryCustom<Entity>

    override fun saveEvent(event: XapiEvent){
        if(event.source.equals("0:0:0:0:0:0:0:1")){
            event.source = "localhost"
        }
        repo.create(event)
    }

    override fun getEvent(id: String) : XapiEvent? = repo.findById(id, XapiEvent::class.java) as XapiEvent

    override fun getAllEvents() : List<XapiEvent>? {
        val events: List<XapiEvent>? = repo.findAll(XapiEvent::class.java)?.filterIsInstance<XapiEvent>()
        return events
    }

    override fun get10Events(from: Int) : List<XapiEvent>? {
        return repo.find10(XapiEvent::class.java, from)?.filterIsInstance<XapiEvent>()
    }

    override fun get20Events(from: Int) : List<XapiEvent>? {
        return repo.find20(XapiEvent::class.java, from)?.filterIsInstance<XapiEvent>()
    }

    override fun get50Events(from: Int) : List<XapiEvent>? {
        return repo.find50(XapiEvent::class.java, from)?.filterIsInstance<XapiEvent>()
    }

    override fun getEventsLimitFrom(limit: Int, from: Int) : List<XapiEvent>? {
        return repo.findAllLimitSkip(XapiEvent::class.java, limit, from)?.filterIsInstance<XapiEvent>()
    }

    override fun getCount() : Long = repo.getCount(XapiEvent::class.java)
}