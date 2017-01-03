package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.models.Actor

interface AgentsService {

    fun getAgentDetails(actor: Actor) : Actor?
    fun getAll() : List<Actor>?
    fun getCount() : Long
    fun exists(actor: Actor) : Boolean

}