package org.lrs.kmodernlrs.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class UserAccountAuth : Authentication {
	
	private var name: String = ""
	private var token: String = ""
	private var authenticated: Boolean = false
	private var grantedAuths: Collection<GrantedAuthority> = mutableListOf()
	
	constructor(token: String) {
		this.token = token
	}
	
	override fun isAuthenticated() : Boolean = authenticated
	
	override fun getName() : String = name
	
	override fun  getCredentials() : String = token
	
	override fun getDetails() : Any? {
		return null
	}
	
	override fun getPrincipal() : Any? {
		return null
	}
	
	fun setAuthorities(grantedAuthorities: Collection<GrantedAuthority>){
		this.grantedAuths = grantedAuthorities
	}
	
	override fun getAuthorities() : Collection<GrantedAuthority>? = grantedAuths
	
	override fun setAuthenticated(authenticated: Boolean){
		this.authenticated = authenticated
	}
}