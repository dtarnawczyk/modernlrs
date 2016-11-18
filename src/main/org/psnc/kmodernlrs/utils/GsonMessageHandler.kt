package org.psnc.kmodernlrs.utils

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import kotlin.Annotation
import java.lang.reflect.Type
 
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.MessageBodyWriter
 
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonMessageHandler: MessageBodyWriter<Any>, MessageBodyReader<Any>{
	fun writeTo(obj: Any, type: Class<?>, genericType: Type, annotations: Annotation[], mediaType: MediaType, httpHeaders: MultivaluedMap<String, Any>, entityStream: OutputStream)
		throw IOException, WebApplicationException {
		
	}
}