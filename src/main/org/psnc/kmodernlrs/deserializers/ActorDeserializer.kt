package org.psnc.kmodernlrs.deserializers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.google.gson.JsonDeserializer
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import org.apache.commons.lang3.StringUtils
import org.psnc.kmodernlrs.models.Actor
import org.psnc.kmodernlrs.models.Account

class ActorDeserializer : JsonDeserializer<Actor>{
	
	val log = LoggerFactory.getLogger(ActorDeserializer::class.java)
	
	override fun deserialize(json: JsonElement?, typeOf: Type, context: JsonDeserializationContext): Actor? {
		if(json == null) {
			return null
		}
		var jsonObject: JsonObject = json as JsonObject
		var actor: Actor = Actor()
		var objectType = jsonObject.get("objectType")
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
		}
		
		return actor
	}
	
	fun deserializeIFI(context: JsonDeserializationContext, jsonObject: JsonObject, actor: Actor) {
		var mbox: String? = jsonObject.get("mbox")?.getAsString()
		var mbox_sha1sum: String? = jsonObject.get("mbox_sha1sum")?.getAsString()
		var openid: String? = jsonObject.get("mbox_sha1sum")?.getAsString()
		var account: Account? = context.deserialize(jsonObject.get("account"), Account::class.java)
		actor.account = account
		actor.mbox = mbox
		actor.mbox_sha1sum = mbox_sha1sum
		actor.openid = openid
	}
	
}