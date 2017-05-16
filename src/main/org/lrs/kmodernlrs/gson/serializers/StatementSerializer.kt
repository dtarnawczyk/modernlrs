package org.lrs.kmodernlrs.gson.serializers

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.lrs.kmodernlrs.domain.Statement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Type

class StatementSerializer : JsonSerializer<Statement> {
	
	val log: Logger = LoggerFactory.getLogger(StatementSerializer::class.java)
	
	override fun serialize(statement: Statement?, typeOfSrc: Type?, context: JsonSerializationContext?) : JsonElement? {
		if(statement == null) {
			return null
		}
		val newStatement = JsonObject()
		newStatement.addProperty("id", statement.id)
		newStatement.add("actor", context?.serialize(statement.actor))
		newStatement.add("verb", context?.serialize(statement.verb))
		newStatement.add("object", context?.serialize(statement.xapiObj))
		newStatement.add("result", context?.serialize(statement.result))
		newStatement.add("context", context?.serialize(statement.context))
		newStatement.add("timestamp", context?.serialize(statement.timestamp))
		newStatement.add("stored", context?.serialize(statement.stored))
		newStatement.add("authority", context?.serialize(statement.authority))
		newStatement.add("attachments", context?.serialize(statement.attachments))
		if(!statement.version.isNullOrEmpty()) newStatement.addProperty("version", statement.version)
		log.debug(">>> Statement serialized:" + newStatement)
		
		return newStatement
	}
	
}