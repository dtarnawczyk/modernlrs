package org.psnc.kmodernlrs.models

import java.io.Serializable

public data class Activity(var type: String? = "",
						   var name: Map<String, String>? = mapOf(),
						   var description: Map<String, String> = mapOf(),
						   var moreInfo: String? = "",
						   var interactionType: InteractionType? = null,
						   var correctResponsesPattern: List<String>? = listOf(),
						   var choices: List<InteractionComponent>? = listOf(),
						   var scale: List<InteractionComponent>? = listOf(),
						   var source: List<InteractionComponent>? = listOf(),
						   var target: List<InteractionComponent>? = listOf(),
						   var steps: List<InteractionComponent>? = listOf(),
						   var extensions: Map<String, Any>? = mapOf() ) : Serializable { 
		
	companion object {
		private val serialVersionUID:Long = 1
	}
}