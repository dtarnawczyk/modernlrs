package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.event.XapiEvent
import org.psnc.kmodernlrs.repository.RepositoryCustom
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class EventServiceImpl : EventService {

    val log: Logger = LoggerFactory.getLogger(AgentsServiceImpl::class.java)

    @Autowired
    lateinit var repo: RepositoryCustom

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

    override fun getCount() : Long = repo.getCount(XapiEvent::class.java)
}