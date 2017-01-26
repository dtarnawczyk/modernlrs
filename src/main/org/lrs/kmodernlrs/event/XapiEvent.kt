package org.lrs.kmodernlrs.event

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

@Document(collection = "events")
data class XapiEvent(
        var user: String = "",
        var date: String = "",
        var xapiData: XapiEventData? = null,
        var method: String? = "", /* GET, PUT, POST */
        var source: String = "",
        @Id
        var id: String = UUID.randomUUID().toString()
    ) : Serializable {

    companion object {
        private val serialVersionUID:Long = 1
    }

    override fun toString() : String {
        return "xAPI Event - user: $user, time: $date, object: $xapiData, method: $method, IP: $source"
    }

}