package org.psnc.kmodernlrs.models

import java.io.Serializable

public data class InteractionComponent(var id: String = "",
									   var description: Map<String, String> = mapOf()) : Serializable {
	companion object {
		private val serialVersionUID:Long = 1
	}
}