package org.psnc.kmodernlrs.models

public data class Actor(var objectType: String = "",
						var name: String = "",
						var mbox: String = "",
						var mbox_sha1sum: String = "",
						var openid: String = "",
						var member: List<Actor> = arrayListOf()
						/*var account: Account? */){

}