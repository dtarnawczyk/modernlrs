package org.psnc.kmodernlrs.deserializers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.google.gson.JsonDeserializer
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import org.psnc.kmodernlrs.models.Actor
import org.psnc.kmodernlrs.models.Account

class ActorDeserializer : JsonDeserializer<Actor>{
	
	val log: Logger = LoggerFactory.getLogger(ActorDeserializer::class.java)
	
	override fun deserialize(json: JsonElement?, typeOf: Type, context: JsonDeserializationContext): Actor? {
		if(json == null) {
			return null
		}
		val jsonObject: JsonObject = json as JsonObject
		val actor: Actor = Actor()
		val objectType = jsonObject.get("objectType")
		actor.objectType = objectType?.asString
		actor.name = jsonObject.get("name")?.asString
		if(objectType != null) {
			when(objectType.asString) {
				"Group" -> {
					val listType = object : TypeToken<List<Actor>>() {}.type
					actor.member = context.deserialize(jsonObject.get("member"), listType)
					deserializeIFI(context, jsonObject, actor)
				}
				else -> {
					deserializeIFI(context, jsonObject, actor)
				}
			}
		} else {
			deserializeIFI(context, jsonObject, actor)
		}
		
		return actor
	}
	
	fun deserializeIFI(context: JsonDeserializationContext, jsonObject: JsonObject, actor: Actor) {
		actor.account = context.deserialize(jsonObject.get("account"), Account::class.java)
		actor.mbox = jsonObject.get("mbox")?.asString
		actor.mbox_sha1sum = jsonObject.get("mbox_sha1sum")?.asString
		actor.openid = jsonObject.get("openid")?.asString
	}
	
}