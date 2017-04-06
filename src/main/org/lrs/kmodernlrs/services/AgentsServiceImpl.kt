package org.lrs.kmodernlrs.services

//import org.lrs.kmodernlrs.repository.RepositoryCustomImpl
import org.lrs.kmodernlrs.models.Actor
import org.lrs.kmodernlrs.repository.RepositoryCustom
import org.lrs.kmodernlrs.util.InverseFunctionalIdentifierHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

    override fun get10Agents(from: Int) : List<Actor>? {
        return repo.find10(Actor::class.java, from)?.filterIsInstance<Actor>()
    }

    override fun get20Agents(from: Int) : List<Actor>? {
        return repo.find20(Actor::class.java, from)?.filterIsInstance<Actor>()
    }

    override fun get50Agents(from: Int) : List<Actor>? {
        return repo.find50(Actor::class.java, from)?.filterIsInstance<Actor>()
    }

    override fun getAgentsLimitFrom(limit: Int, from: Int) : List<Actor>? {
        return repo.findAllLimitSkip(Actor::class.java, limit, from)?.filterIsInstance<Actor>()
    }

    override fun getCount() : Long = repo.getCount(Actor::class.java)

    override fun exists(actor: Actor) : Boolean {
        // TODO: implement this
        return false
    }
}