package org.psnc.kmodernlrs.models

import java.io.Serializable

public data class Verb(var id: String,
					   var display: Map<String, String>) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}

}