package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.models.Actor

interface AgentsService {

    fun getAgentDetails(actor: Actor) : Actor?
    fun getAll() : List<Actor>?
    fun get10Agents(from: Int) : List<Actor>?
    fun get20Agents(from: Int) : List<Actor>?
    fun get50Agents(from: Int) : List<Actor>?
    fun getAgentsLimitFrom(limit: Int, from: Int) : List<Actor>?
    fun getCount() : Long
    fun exists(actor: Actor) : Boolean

}