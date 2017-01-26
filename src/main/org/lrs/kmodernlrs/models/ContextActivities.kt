package org.lrs.kmodernlrs.models

import java.io.Serializable

data class ContextActivities(
			var parent: List<XapiObject>? = null,
			var grouping: List<XapiObject>? = null,
			var category: List<XapiObject>? = null,
			var other: List<XapiObject>? = null
		) : Serializable {
	
	companion object {
		private val serialVersionUID:Long = 1
	}
}