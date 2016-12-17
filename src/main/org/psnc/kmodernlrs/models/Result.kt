package org.psnc.kmodernlrs.models

import java.io.Serializable

public data class Result(
		var score: Score? = null,
		var success: Boolean? = null,
		var completion: Boolean? = null,
		var response: String? = "",
		var duraion: String? = "",
		var extension: Any? = null
		) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}
}