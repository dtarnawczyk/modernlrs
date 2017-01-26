package org.lrs.kmodernlrs.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

@Document(collection = "activities")
data class Activity(
        @Id
		var id: String? = UUID.randomUUID().toString(),
        var type: String? = "",
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