package org.lrs.kmodernlrs.models

import java.io.Serializable

data class Account(
		var homePage: String = "",
		var name: String = "") : Serializable {
	companion object {
		private val serialVersionUID:Long = 1
	}
}