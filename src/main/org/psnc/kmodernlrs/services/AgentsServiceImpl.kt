package org.psnc.kmodernlrs.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.psnc.kmodernlrs.models.Actor
import org.psnc.kmodernlrs.repository.RepositoryCustomImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
open class AgentsServiceImpl : AgentsService {

    val log: Logger = LoggerFactory.getLogger(AgentsServiceImpl::class.java)

    @Autowired
    lateinit var repo: RepositoryCustomImpl

    override fun getAgentDetails(actor: Actor) : Actor? {
        val attributes = hashMapOf<String, String>()
        if (!actor.mbox.isNullOrEmpty()) {
            attributes.put("mbox", actor.mbox ?: "")
        }
        if (!actor.mbox_sha1sum.isNullOrEmpty()) {
            attributes.put("mbox_sha1sum", actor.mbox_sha1sum ?: "")
        }
        if (!actor.openid.isNullOrEmpty()) {
            attributes.put("openid", actor.openid ?: "" )
        }
        if (!actor.account?.name.isNullOrEmpty()) {
            attributes.put("account.name", actor.account?.name ?: "")
        }
        if (!actor.account?.homePage.isNullOrEmpty()) {
            attributes.put("account.homePage", actor.account?.homePage ?: "")
        }
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