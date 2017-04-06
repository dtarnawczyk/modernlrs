package org.lrs.kmodernlrs.serializers

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import org.lrs.kmodernlrs.models.Account
import org.lrs.kmodernlrs.models.Actor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Type

class ActorSerializer : JsonSerializer<Actor> {
	
	val log: Logger = LoggerFactory.getLogger(ActorSerializer::class.java)
	
	override fun serialize(actor: Actor?, typeOfSrc: Type?, context: JsonSerializationContext?) : JsonElement? {
		if(actor == null) {
			return null
		}
		val newActor = JsonObject()
		if(!actor.objectType.isNullOrEmpty()) newActor.addProperty("objectType", actor.objectType)
		if(!actor.name.isNullOrEmpty()) newActor.addProperty("name", actor.name)
		if(!actor.mbox.isNullOrEmpty()) newActor.addProperty("mbox", actor.mbox)
		if(!actor.mbox_sha1sum.isNullOrEmpty()) newActor.addProperty("mbox_sha1sum", actor.mbox_sha1sum)
		if(!actor.openid.isNullOrEmpty()) newActor.addProperty("openid", actor.openid)
		val listType = object : TypeToken<List<Actor>>() {}.type
		if(!actor.member.isEmpty()) newActor.add("member", context?.serialize(actor.member, listType))
		if(actor.account != null) newActor.add("account", context?.serialize(actor.account, Account::class.java))
		log.debug(">>> New Actor serialized:" + newActor)
		
		return newActor
	}
}