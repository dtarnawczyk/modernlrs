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
import org.psnc.kmodernlrs.models.XapiObject
import org.psnc.kmodernlrs.models.Activity
import org.psnc.kmodernlrs.models.Actor
import org.psnc.kmodernlrs.models.Account
import org.psnc.kmodernlrs.models.Verb
import org.psnc.kmodernlrs.models.Result
import org.psnc.kmodernlrs.models.Context
import org.psnc.kmodernlrs.models.Attachment

class ObjectDeserializer : JsonDeserializer<XapiObject>{
	
	val log = LoggerFactory.getLogger(ObjectDeserializer::class.java)
	
	override fun deserialize(json: JsonElement?, typeOf: Type, context: JsonDeserializationContext): XapiObject? {
		if(json == null) {
			return null
		}
		var jsonObject: JsonObject = json as JsonObject
		var xapiObject: XapiObject = XapiObject()
		var objectType = jsonObject.get("objectType")
		xapiObject.objectType = objectType?.asString
		if(objectType != null) {
			when(objectType.asString) {
				"Activity" -> {
					xapiObject.id = jsonObject.get("id")?.asString
					var activity: Activity? = context.deserialize(jsonObject.get("definition"), Activity::class.java)
					xapiObject.definition = activity
				}
				"Agent" -> {
					xapiObject.name = jsonObject.get("name")?.asString
					deserializeIFI(context, jsonObject, xapiObject)				
				}
				"Group" -> {
					xapiObject.name = jsonObject.get("name")?.asString
					val listType = object : TypeToken<List<Actor>>() {}.type
					xapiObject.member = context.deserialize(jsonObject.get("member"), listType)
					deserializeIFI(context, jsonObject, xapiObject)
				}
				"SubStatement" -> {
					xapiObject.actor = context.deserialize(jsonObject.get("actor"), Actor::class.java)
					xapiObject.verb = context.deserialize(jsonObject.get("verb"), Verb::class.java)
					xapiObject.xapiObj = context.deserialize(jsonObject.get("object"), XapiObject::class.java)
					xapiObject.result = context.deserialize(jsonObject.get("result"), Result::class.java)
					xapiObject.context = context.deserialize(jsonObject.get("context"), Context::class.java)
					val listType = object : TypeToken<List<Attachment>>() {}.type
					xapiObject.attachments = context.deserialize(jsonObject.get("attachments"), listType)
				}
				"StatementRef" -> {
					xapiObject.id = jsonObject.get("id")?.asString
				}
				else -> {}
			}
		}
		
		return xapiObject
	}
	
	fun deserializeIFI(context: JsonDeserializationContext, jsonObject: JsonObject, xapiObject: XapiObject) {
		xapiObject.account = context.deserialize(jsonObject.get("account"), Account::class.java)
		xapiObject.mbox = jsonObject.get("mbox")?.getAsString()
		xapiObject.mbox_sha1sum = jsonObject.get("mbox_sha1sum")?.getAsString()
		xapiObject.openid = jsonObject.get("openid")?.getAsString()
	}
}