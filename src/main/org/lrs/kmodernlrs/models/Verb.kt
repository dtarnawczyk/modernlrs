package org.lrs.kmodernlrs.models

import java.io.Serializable

data class Verb(var id: String = "",
					   var display: Map<String, String> = emptyMap<String, String>()) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}

}