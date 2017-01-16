package org.psnc.kmodernlrs.security

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document(collection = "users")
data class UserAccount(
		var name: String = "",
		var password: String = "",
		var role: String = "",
		var email: String = "",
		var token: String = "",
		var tokenExpirationDate: LocalDate? = null,
		@Id
		var id: String = UUID.randomUUID().toString(),
		var createdTime: LocalDate? = null,
		var active: Boolean = false) {
}