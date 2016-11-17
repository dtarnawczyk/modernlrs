package org.psnc.kmodernlrs.models


class Statement(val id: String, val actor: Actor, val verb: Verb, val xapiObj: XapiObject, val reult: Result, val context: Context, val timestamp: String, val stored: String, val authority: Actor, val version: String) {

}