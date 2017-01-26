package org.lrs.kmodernlrs.models

import java.io.Serializable

data class InteractionComponent(var id: String = "",
									   var description: Map<String, String> = mapOf()) : Serializable {
	companion object {
		private val serialVersionUID:Long = 1
	}
}