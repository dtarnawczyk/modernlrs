package org.lrs.kmodernlrs.domain

import java.io.Serializable

data class Account(
		var homePage: String = "",
		var name: String = "") : Serializable {
	companion object {
		private val serialVersionUID:Long = 1
	}
}