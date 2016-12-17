package org.psnc.kmodernlrs.models

import java.io.Serializable

public data class ContextActivities(
			var parent: List<XapiObject>? = null,
			var grouping: List<XapiObject>? = null,
			var category: List<XapiObject>? = null,
			var other: List<XapiObject>? = null
		) : Serializable{
	
	companion object {
		private val serialVersionUID:Long = 1
	}
}