package org.psnc.kmodernlrs.models

import java.io.Serializable

public data class Account(
		var homePage: String = "",
		var name: String = "") : Serializable {
	companion object {
		private val serialVersionUID:Long = 1
	}
}