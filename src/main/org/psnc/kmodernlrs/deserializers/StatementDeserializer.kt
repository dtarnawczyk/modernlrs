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
import java.util.UUID

import org.psnc.kmodernlrs.models.Statement
import org.psnc.kmodernlrs.models.Actor
import org.psnc.kmodernlrs.models.Verb
import org.psnc.kmodernlrs.models.XapiObject
import org.psnc.kmodernlrs.models.Result
import org.psnc.kmodernlrs.models.Context
import org.psnc.kmodernlrs.models.Attachment

class StatementDeserializer : JsonDeserializer<Statement>{
	
	val log = LoggerFactory.getLogger(StatementDeserializer::class.java)
	
	override fun deserialize(json: JsonElement?, typeOf: Type, context: JsonDeserializationContext): Statement? {
		if(json == null) {
			return null
		}
		var jsonObject: JsonObject = json as JsonObject
		var id:String
		
		if(jsonObject.get("id") == null || StringUtils.isBlank(jsonObject.get("id").asString as CharSequence)){
			id = UUID.randomUUID().toString()
		} else {
			id = jsonObject.get("id").getAsString()
		}
		
		var actor: Actor = context.deserialize(jsonObject.get("actor"), Actor::class.java)
		var verb:Verb = context.deserialize(jsonObject.get("verb"), Verb::class.java)
		var obj:XapiObject = context.deserialize(jsonObject.get("object"), XapiObject::class.java)
		var result:Result? = context.deserialize(jsonObject.get("result"), Result::class.java)
		var ctx:Context? = context.deserialize(jsonObject.get("context"), Context::class.java)
		var authority:Actor? = context.deserialize(jsonObject.get("authority"), Actor::class.java)
		var version: String? = jsonObject.get("version")?.getAsString()
		val listType = object : TypeToken<List<Attachment>>() {}.type
		var attachments: List<Attachment>? = context.deserialize(jsonObject.get("attachments"), listType)
		
		var statement:Statement = Statement(id, actor, verb, obj, result, ctx, null, null, authority, version, attachments)
		log.debug(">>> Deserialized Statement: " + statement)
		return statement
	}
}