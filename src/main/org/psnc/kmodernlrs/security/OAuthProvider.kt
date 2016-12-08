package org.psnc.kmodernlrs.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Primary
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

@ConditionalOnProperty(name=arrayOf("auth.type"), havingValue="oauth")
@Component
@Primary
class OAuthProvider : AuthenticationProvider{
	
	override fun authenticate(auth: Authentication) : Authentication {
		// TODO implement it
		return auth
	}
	
	override fun supports(authentication: Class<*>) : Boolean {
		return authentication.equals(UserAccountAuth::class.java)
	}
}