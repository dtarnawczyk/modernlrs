package org.lrs.kmodernlrs.event

import java.io.Serializable

class XapiEventData(
        var objectType: String = "",
        var identifier: Any? = null ) : Serializable {

    companion object {
        private val serialVersionUID:Long = 1
    }
}