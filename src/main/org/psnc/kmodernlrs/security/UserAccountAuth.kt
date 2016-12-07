package org.psnc.kmodernlrs.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class UserAccountAuth : Authentication {
	
	private var name: String = ""
	private var token: String = ""
	private var authenticated: Boolean = false
	private var grantedAuths: Collection<GrantedAuthority> = mutableListOf<GrantedAuthority>()
	
	constructor(token: String) {
		this.token = token
	}
	
	override fun isAuthenticated() : Boolean {
		return authenticated
	}
	
	override fun getName() : String {
		return name
	}
	
	override fun  getCredentials() : String {
		return token
	}
	
	override fun getDetails() : Any? {
		return null
	}
	
	override fun getPrincipal() : Any? {
		return null
	}
	
	fun setAuthorities(grantedAuthorities: Collection<GrantedAuthority>){
		this.grantedAuths = grantedAuthorities
	}
	
	override fun getAuthorities() : Collection<GrantedAuthority>? {
		return grantedAuths
	}
	
	override fun setAuthenticated(authenticated: Boolean){
		this.authenticated = authenticated
	}
}