package org.lrs.kmodernlrs.deserializers

//import org.apache.commons.lang3.StringUtils
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.lrs.kmodernlrs.models.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Type

class StatementDeserializer : JsonDeserializer<Statement> {
	
	val log: Logger = LoggerFactory.getLogger(StatementDeserializer::class.java)
	
	override fun deserialize(json: JsonElement?, typeOf: Type, context: JsonDeserializationContext): Statement? {
		if(json == null) {
			return null
		}
		val jsonObject: JsonObject = json as JsonObject

		val id:String = jsonObject.get("id").asString
		val actor: Actor = context.deserialize(jsonObject.get("actor"), Actor::class.java)
		val verb:Verb = context.deserialize(jsonObject.get("verb"), Verb::class.java)
		val obj:XapiObject = context.deserialize(jsonObject.get("object"), XapiObject::class.java)
		val result: Result? = context.deserialize(jsonObject.get("result"), Result::class.java)
		val ctx:Context? = context.deserialize(jsonObject.get("context"), Context::class.java)
		val authority:Actor? = context.deserialize(jsonObject.get("authority"), Actor::class.java)
		val version: String? = jsonObject.get("version")?.asString
		val listType = object : TypeToken<List<Attachment>>() {}.type
		val attachments: List<Attachment>? = context.deserialize(jsonObject.get("attachments"), listType)

		val statement:Statement = Statement(id, actor, verb, obj, result, ctx, null, null, authority, version, attachments)
//		var statement:Statement = Statement(id, actor, version)
		log.debug(">>> Deserialized Statement: $statement")
		return statement
	}
}