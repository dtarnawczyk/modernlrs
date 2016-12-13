package org.psnc.kmodernlrs.models

import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Timestamp

public data class Statement(
		
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#stmtid)
		 */
		var id: String = "",
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#actor)
		 */
		var actor: Actor,
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#verb)
		 */
		var verb: Verb,
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#object)
		 */
		@SerializedName("object")
		var xapiObj: XapiObject,
//		var reult: Result?,
//		var context: Context?,
//		var timestamp: String?,
		/**
 		 * Optional (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#stored)
		 */
		var stored: Timestamp? = null,
//		var authority: Actor?,
		var version: String? = "") : Serializable { 
		
	companion object {
		private val serialVersionUID = 1L
	}
	
	fun validate() {
	}
}