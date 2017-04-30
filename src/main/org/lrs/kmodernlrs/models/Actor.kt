package org.lrs.kmodernlrs.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

//@CompoundIndex(name = "actor_idx", def = "{'mbox' : 1, 'mbox_sha1sum' : 1," +
//				" 'openid' : 1, 'account.name' : 1, 'account.homePage' : 1}")
@Document(collection = "actors")
data class Actor(
		@Id
		override var id: String = UUID.randomUUID().toString(),
		/**
		 * Optional (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#242-actor)
		 * Possible options: "Agent", "Group" or none
		 */
		var objectType: String? = "",
		var name: String? = "",
		var mbox: String? = "",
		var mbox_sha1sum: String? = "",
		var openid: String? = "",
		var member: List<Actor> = listOf(),
		var account: Account? = null
	) : Serializable, Entity {
	
	companion object {
		private val serialVersionUID:Long = 1
	}
}