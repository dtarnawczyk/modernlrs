package org.psnc.kmodernlrs.models

import java.io.Serializable

public data class Score(
		var scaled: Double? = null,
		var raw: Double? = null,
		var min: Double? = null,
		var max: Double? = null
		) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}
}