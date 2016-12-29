package org.psnc.kmodernlrs.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Timestamp
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "statements")
public data class Statement(
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#stmtid)
		 */
		@Id var id: String = "",
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
		var result: Result?,
		var context: Context?,
		var timestamp: Timestamp? = null,
		/**
 		 * Optional (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#stored)
		 */
		var stored: Timestamp? = null,
		var authority: Actor? = null,
		var version: String? = "1.0.3",
		var attachments: List<Attachment>? = listOf()
	) : Serializable {
		
	companion object {
		private val serialVersionUID:Long = 1
	}
	
	fun validate() {
	}
}