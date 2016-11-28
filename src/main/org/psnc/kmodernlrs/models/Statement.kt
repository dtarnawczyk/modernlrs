package org.psnc.kmodernlrs.models

import com.google.gson.JsonParseException
import java.io.Serializable
import javax.persistence.*


@Entity
public data class Statement(
		
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#stmtid)
		 */
		@Id var id: String = "",
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#actor)
		 */
//		var actor: Actor,
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#verb)
		 */
//		var verb: Verb,
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#object)
		 */
//		var xapiObj: XapiObject,
//		var reult: Result?,
//		var context: Context?,
//		var timestamp: String?,
//		var stored: String,
//		var authority: Actor?,
		var version: String = "") : Serializable { 
		
	companion object {
		private val serialVersionUID = 1L
	}
	
	fun validate() {
	}
}