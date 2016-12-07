package org.psnc.kmodernlrs.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component("OAuthProvider")
class OAuthProvider : AuthenticationProvider{
	
	override fun authenticate(auth: Authentication) : Authentication {
		// TODO implement it
		return auth
	}
	
	override fun supports(authentication: Class<*>) : Boolean {
		return authentication.equals(UserAccountAuth::class.java)
	}
}