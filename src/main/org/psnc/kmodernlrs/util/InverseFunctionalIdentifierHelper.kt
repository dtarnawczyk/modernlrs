package org.psnc.kmodernlrs.util

import org.psnc.kmodernlrs.models.Actor
import java.util.*

object InverseFunctionalIdentifierHelper {
    @JvmStatic fun getAvailableAttrsFromActor(actor: Actor): HashMap<String, String> {
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
        return attributes
    }
}
