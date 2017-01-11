package org.psnc.kmodernlrs.event

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import java.io.Serializable
import java.util.UUID

@Document(collection = "events")
public data class XapiEvent(
        var user: String = "",
        var time: String = "",
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
        return "xAPI Event - user: $user, time: $time, object: $xapiData, method: $method, IP: $source"
    }

}