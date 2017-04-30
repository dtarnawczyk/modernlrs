package org.lrs.kmodernlrs.security

import org.lrs.kmodernlrs.models.Entity
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
		override var id: String = UUID.randomUUID().toString(),
        var createdTime: LocalDate? = null,
        var active: Boolean = false) : Entity