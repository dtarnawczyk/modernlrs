package org.lrs.kmodernlrs.models

import com.google.gson.annotations.SerializedName
import org.lrs.kmodernlrs.mongo.Activity
import org.lrs.kmodernlrs.mongo.CascadeSave
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable

@Document(collection = "statements")
data class Statement(
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#stmtid)
		 */
		@Id
		override var id: String = "",
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#actor)
		 */
		@DBRef
		@CascadeSave
		var actor: Actor? = null,
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#verb)
		 */
		var verb: Verb? = null,
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#object)
		 */
		@SerializedName("object")
		@Field("object")
		@Activity
		var xapiObj: XapiObject? = null,
		var result: Result? = null,
		var context: Context? = null,
		var timestamp: String? = null,
		/**
 		 * Optional (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#stored)
		 */
		var stored: String? = null,
		var authority: Actor? = null,
		var version: String? = "1.0.3",
		var attachments: List<Attachment>? = listOf()
	) : Serializable, Entity {

	companion object {
		private val serialVersionUID:Long = 1
	}
	
	fun validate() {
	}
}