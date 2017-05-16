package org.lrs.kmodernlrs.domain

import java.io.Serializable

data class Verb(var id: String = "",
					   var display: Map<String, String> = emptyMap<String, String>()) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}

}