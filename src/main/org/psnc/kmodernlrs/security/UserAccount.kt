package org.psnc.kmodernlrs.security

import java.util.Date

data class UserAccount(
		var name: String = "user",
		var password: String = "",
		var id: String = "0",
		var createdTime: Date,
		var active: Boolean = false) {
}