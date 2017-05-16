package org.lrs.kmodernlrs.domain

import java.io.Serializable

data class StatementRef(
		var objectType: String? = "",
		var id:String? = ""
		) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}
}