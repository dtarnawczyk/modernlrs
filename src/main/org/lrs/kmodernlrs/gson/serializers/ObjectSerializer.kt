package org.lrs.kmodernlrs.gson.serializers

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import org.lrs.kmodernlrs.domain.Actor
import org.lrs.kmodernlrs.domain.Attachment
import org.lrs.kmodernlrs.domain.XapiObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Type

class ObjectSerializer : JsonSerializer<XapiObject> {
	
	val log: Logger = LoggerFactory.getLogger(ObjectSerializer::class.java)
	
	override fun serialize(xapiObject: XapiObject?, typeOfSrc: Type?, context: JsonSerializationContext?) : JsonElement? {
		if(xapiObject == null) {
			return null
		}
		val newObject: JsonObject = JsonObject()
		if(!xapiObject.id.isNullOrEmpty()) newObject.addProperty("id", xapiObject.id)
		if(!xapiObject.objectType.isNullOrEmpty()) newObject.addProperty("objectType", xapiObject.objectType)
		if(xapiObject.definition != null) newObject.add("definition", context?.serialize(xapiObject.definition))
		if(!xapiObject.name.isNullOrEmpty()) newObject.addProperty("name", xapiObject.name)
		if(!xapiObject.mbox.isNullOrEmpty()) newObject.addProperty("mbox", xapiObject.mbox)
		if(!xapiObject.mbox_sha1sum.isNullOrEmpty()) newObject.addProperty("mbox_sha1sum", xapiObject.mbox_sha1sum)
		if(!xapiObject.openid.isNullOrEmpty()) newObject.addProperty("openid", xapiObject.openid)
		val listType = object : TypeToken<List<Actor>>() {}.type
		if(!xapiObject.member.isEmpty()) newObject.add("member", context?.serialize(xapiObject.member, listType))
		if(xapiObject.account != null) newObject.add("account", context?.serialize(xapiObject.account))
		if(xapiObject.actor != null) newObject.add("actor", context?.serialize(xapiObject.actor))
		if(xapiObject.verb != null) newObject.add("verb", context?.serialize(xapiObject.verb))
		if(xapiObject.xapiObj != null) newObject.add("object", context?.serialize(xapiObject.xapiObj))					 
		if(xapiObject.result != null) newObject.add("result", context?.serialize(xapiObject.result))
		if(xapiObject.context != null) newObject.add("context", context?.serialize(xapiObject.context))
		if(xapiObject.timestamp != null) newObject.add("timestamp", context?.serialize(xapiObject.timestamp))
		val attachmentListType = object : TypeToken<List<Attachment>>() {}.type
		if(xapiObject.attachments != null) newObject.add("attachments", context?.serialize(xapiObject.attachments, attachmentListType))
		
		log.debug(">>> Object serialized:" + newObject)
		
		return newObject
	}
}