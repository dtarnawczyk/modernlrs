package org.psnc.kmodernlrs.models

import java.io.Serializable
import com.google.gson.annotations.SerializedName

public data class XapiObject(
		var id: String? = "",
		var objectType: String? = "",
		var definition: Activity? = null,
							 
		// when the Object is an Agent/Group - attributes of an Actor
		var name: String? = "",
		var mbox: String? = "",
		var mbox_sha1sum: String? = "",
		var openid: String? = "",
		var member: List<Actor> = listOf(),
		var account: Account? = null,
									 
		// when an Object is an Substatement - attributes of a Statement
		var actor: Actor? = null,
		var verb: Verb? = null,
		@SerializedName("object")
		var xapiObj: XapiObject? = null,
		var result: Result? = null,
		var context: Context? = null,
		var timestamp: String? = null,
		var attachments: List<Attachment>? = listOf()) : Serializable {
	companion object {
		private val serialVersionUID:Long = 1
	}
}