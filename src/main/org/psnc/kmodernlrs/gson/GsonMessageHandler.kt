package org.psnc.kmodernlrs.gson

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import kotlin.Annotation
import java.lang.reflect.Type

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
 
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.MessageBodyWriter
 
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import org.psnc.kmodernlrs.models.*
import org.psnc.kmodernlrs.serializers.*
import org.psnc.kmodernlrs.deserializers.*

import org.springframework.stereotype.Component

@Component
open class GsonMessageHandler: MessageBodyReader<Any>, MessageBodyWriter<Any>, GsonFactoryProvider{
	
    private var gson: Gson
		
	var charset:Charset = StandardCharsets.UTF_8
	
	constructor() {
		var gsonBuilder:GsonBuilder = GsonBuilder()
		gson = gsonBuilder
				.disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
				.registerTypeAdapter(Statement::class.java, StatementSerializer())
				.registerTypeAdapter(Statement::class.java, StatementDeserializer())
				.registerTypeAdapter(Actor::class.java, ActorSerializer())
				.registerTypeAdapter(Actor::class.java, ActorDeserializer())
                .setPrettyPrinting()
                .serializeNulls()
                .create()
	}
	
	override fun gsonFactory(): Gson{
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
        } catch (e:UnsupportedEncodingException) {
            e.printStackTrace()
        }
		try {
			var jsonType: Type = if (type.equals(genericType)) {
				type
			} else {
				genericType
			}
			return gson.fromJson(streamReader, jsonType)
		} finally {
			try {
	            if( streamReader != null) streamReader.close()
	        } catch (e:IOException) {
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
		var writer:OutputStreamWriter = OutputStreamWriter(entityStream, charset)
		try {
            var jsonType:Type = if (type.equals(genericType)) {
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