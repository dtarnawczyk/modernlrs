package org.psnc.kmodernlrs.event

import java.io.Serializable

public data class XapiEventData(
        var objectType: String = "",
        var identifier: Any? = null ) : Serializable {

    companion object {
        private val serialVersionUID:Long = 1
    }
}