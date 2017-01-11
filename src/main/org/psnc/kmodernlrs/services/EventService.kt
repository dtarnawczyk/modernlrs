package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.event.XapiEvent

interface EventService {
    fun saveEvent(event: XapiEvent)
    fun getEvent(id: String) : XapiEvent?
    fun getAllEvents() : List<XapiEvent>?
    fun getCount() : Long
}
