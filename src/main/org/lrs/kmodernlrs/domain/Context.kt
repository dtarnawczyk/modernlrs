package org.lrs.kmodernlrs.domain

import java.io.Serializable

data class Context(
        var registration: String? = "",
        var instructor: Actor? = null,
        var team: Actor? = null,
        var contextActivities: ContextActivities? = null,
        var revision: String? = "",
        var platform: String? = "",
        var language: String? = "",
        var statement: StatementRef? = null,
        var extension: Any? = null
		) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}
}