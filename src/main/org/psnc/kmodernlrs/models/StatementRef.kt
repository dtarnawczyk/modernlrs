package org.psnc.kmodernlrs.models

import java.io.Serializable

public data class StatementRef(
		var objectType: String? = "",
		var id:String? = ""
		) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}
}