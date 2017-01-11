package org.psnc.kmodernlrs.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.psnc.kmodernlrs.models.Actor
import org.psnc.kmodernlrs.repository.RepositoryCustom
import org.psnc.kmodernlrs.util.InverseFunctionalIdentifierHelper
//import org.psnc.kmodernlrs.repository.RepositoryCustomImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
open class AgentsServiceImpl : AgentsService {

    val log: Logger = LoggerFactory.getLogger(AgentsServiceImpl::class.java)

    @Autowired
    lateinit var repo: RepositoryCustom

    override fun getAgentDetails(actor: Actor) : Actor? {
        val attributes = InverseFunctionalIdentifierHelper.getAvailableAttrsFromActor(actor)
        return repo.fimdByAttrs(attributes, Actor::class.java) as Actor?
    }

    override fun getAll() : List<Actor>? {

        val actors: List<Actor>? = repo.findAll(Actor::class.java)?.filterIsInstance<Actor>()
        return actors
//        return repo.findAll(Actor::class.java) as List<Actor>?
    }

    override fun getCount() : Long = repo.getCount(Actor::class.java)

    override fun exists(actor: Actor) : Boolean {
        // TODO: implement this
        return false
    }
}