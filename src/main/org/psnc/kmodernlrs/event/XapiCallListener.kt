package org.psnc.kmodernlrs.event

import org.psnc.kmodernlrs.services.EventService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
open class XapiCallListener {

    val log: Logger = LoggerFactory.getLogger(XapiCallListener::class.java)

    @Autowired lateinit var service: EventService

    @Async
    @EventListener
    open fun handle(event: XapiEvent) {
        log.debug(">>> Saving event: $event")
        service.saveEvent(event)
    }
}
