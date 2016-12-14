package org.psnc.kmodernlrs.deserializers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.google.gson.JsonDeserializer
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import org.apache.commons.lang3.StringUtils
import java.util.UUID

import org.psnc.kmodernlrs.models.Statement
import org.psnc.kmodernlrs.models.Actor
import org.psnc.kmodernlrs.models.Verb
import org.psnc.kmodernlrs.models.XapiObject

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
		
		log.debug(">>> Deserialized Actor: " + actor)
		log.debug(">>> Deserialized Verb: " + verb)
		log.debug(">>> Deserialized Object: " + obj)
		
		var version: String? = jsonObject.get("version")?.getAsString()
		
		var statement:Statement = Statement(id, actor, verb, obj, null, version)
		log.debug(">>> Deserialized Statement: " + statement)
		return statement
	}
}