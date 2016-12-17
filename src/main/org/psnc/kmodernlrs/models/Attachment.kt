package org.psnc.kmodernlrs.models

import java.io.Serializable

public data class Attachment(
		var usageType: String? = "",
		var display: Map<String, String> = mapOf(),
		var description: Map<String, String> = mapOf(),
		var contentType: String? = "",
		var length: Int? = null,
		var sha2: String? = "",
		var fileUrl: String? = "") : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}
	
}