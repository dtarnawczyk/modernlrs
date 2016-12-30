package org.psnc.kmodernlrs.models

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import java.io.Serializable

//@Document(collection = "actors")
public data class Actor(
		
		/**
		 * Optional (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#242-actor)
		 * Possible options: "Agent", "Group" or none
		 */
		var objectType: String? = "",
		var name: String? = "",
//		@Id
		var mbox: String? = "",
		var mbox_sha1sum: String? = "",
		var openid: String? = "",
		var member: List<Actor> = listOf(),
		var account: Account? = null
	) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}
}