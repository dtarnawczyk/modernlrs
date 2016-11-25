package org.psnc.kmodernlrs.models

import com.google.gson.JsonParseException
import java.io.Serializable

public data class Statement(
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#stmtid)
		 */
		val id: String?,
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#actor)
		 */
//		val actor: Actor,
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#verb)
		 */
//		val verb: Verb,
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#object)
		 */
//		val xapiObj: XapiObject,
//		val reult: Result?,
//		val context: Context?,
//		val timestamp: String?,
//		val stored: String,
//		val authority: Actor?,
		val version: String?): Serializable {
	
	companion object {
		private val serialVersionUID = 1L
	}
	
	fun validate() {
		if (id == null) throw JsonParseException("'uuid' is null!")
	}
}