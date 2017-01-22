package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.event.XapiEvent

interface EventService {
    fun saveEvent(event: XapiEvent)
    fun getEvent(id: String) : XapiEvent?
    fun getAllEvents() : List<XapiEvent>?
    fun get10Events(from: Int) : List<XapiEvent>?
    fun get20Events(from: Int) : List<XapiEvent>?
    fun get50Events(from: Int) : List<XapiEvent>?
    fun getEventsLimitFrom(limit: Int, from: Int) : List<XapiEvent>?
    fun getCount() : Long
}
