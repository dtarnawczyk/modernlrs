package org.lrs.kmodernlrs.gson

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.lrs.kmodernlrs.deserializers.ActorDeserializer
import org.lrs.kmodernlrs.deserializers.ObjectDeserializer
import org.lrs.kmodernlrs.deserializers.StatementDeserializer
import org.lrs.kmodernlrs.models.Actor
import org.lrs.kmodernlrs.models.Statement
import org.lrs.kmodernlrs.models.XapiObject
import org.lrs.kmodernlrs.serializers.ActorSerializer
import org.lrs.kmodernlrs.serializers.ObjectSerializer
import org.lrs.kmodernlrs.serializers.StatementSerializer
import org.springframework.stereotype.Component
import java.io.*
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.MessageBodyWriter

@Component
open class GsonMessageHandler: MessageBodyReader<Any>, MessageBodyWriter<Any>, GsonFactoryProvider {
	
    private var gson: Gson
		
	var charset: Charset = StandardCharsets.UTF_8
	
	constructor() {
		val gsonBuilder: GsonBuilder = GsonBuilder()
		gson = gsonBuilder
				.disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
				.registerTypeAdapter(Statement::class.java, StatementSerializer())
				.registerTypeAdapter(Statement::class.java, StatementDeserializer())
				.registerTypeAdapter(Actor::class.java, ActorSerializer())
				.registerTypeAdapter(Actor::class.java, ActorDeserializer())
				.registerTypeAdapter(XapiObject::class.java, ObjectDeserializer())
				.registerTypeAdapter(XapiObject::class.java, ObjectSerializer())
                .setPrettyPrinting()
                .serializeNulls()
                .create()
	}
	
	override fun gsonFactory(): Gson {
		return gson 
	}
	
	@Override
    override fun isReadable(type: Class<*>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType): Boolean {
        return true
    }
	
	@Override
	override fun readFrom(type: Class<Any>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType, httpHeaders: MultivaluedMap<String, String>, entityStream: InputStream): Any {
		var streamReader: InputStreamReader? = null
		try {
            streamReader = InputStreamReader(entityStream, charset)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
		try {
			val jsonType: Type = if (type.equals(genericType)) {
				type
			} else {
				genericType
			}
			return gson.fromJson(streamReader, jsonType)
		} finally {
			try {
	            if( streamReader != null) streamReader.close()
	        } catch (e: IOException) {
	            e.printStackTrace()
	        }
		}	
	}
	
	@Override
    override fun isWriteable(type: Class<*>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType): Boolean {
        return true
    }
	
	@Override
	override fun writeTo(obj: Any, type: Class<*>,
                         genericType: Type,
                         annotations: Array<Annotation>,
                         mediaType: MediaType,
                         httpHeaders: MultivaluedMap<String, Any>,
                         entityStream: OutputStream) {
		val writer: OutputStreamWriter = OutputStreamWriter(entityStream, charset)
		try {
            val jsonType: Type = if (type.equals(genericType)) {
                type
            } else {
                genericType
            }
            gson.toJson(obj, jsonType, writer)
        } finally {
            writer.close()
        }
	}
	
	@Override
	override fun getSize(obj: Any, type: Class<*>, genericType: Type, arg3: Array<Annotation>, mediaType: MediaType): Long {
		return -1
	}
	
}