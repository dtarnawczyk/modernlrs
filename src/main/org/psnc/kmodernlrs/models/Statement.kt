package org.psnc.kmodernlrs.models

class Statement() {
	
	companion object {
        private val serialVersionUID = 1L
    }
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#stmtid)
		 */
	lateinit var id: String
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#actor)
		 */
//		lateinit var actor: Actor
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#verb)
		 */
//		lateinit var verb: Verb
		/**
		 * Required (https://github.com/adlnet/xAPI-Spec/blob/master/xAPI-Data.md#object)
		 */
//		lateinit var xapiObj: XapiObject
//		lateinit var reult: Result
//		lateinit var context: Context
//		lateinit var timestamp: String
//		lateinit var stored: String
//		lateinit var authority: Actor
	lateinit var version: String
	
	constructor(
       id: String, 
       version: String): this() {
          this.id = id
          this.version = version
    }
	
	override fun toString() : String {
		return "This Statement has ID: "+ this.id + ", version: "+ this.version
	}
}