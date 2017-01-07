package org.psnc.kmodernlrs.security

import org.springframework.security.authentication.AuthenticationProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Primary
import org.springframework.beans.factory.annotation.Value
import org.apache.commons.lang3.StringUtils
import org.apache.commons.codec.binary.Base64
import java.nio.charset.Charset

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

@ConditionalOnProperty(name=arrayOf("auth.type"), havingValue="basic")
@Component
@Primary
class BasicAuthProvider : AuthenticationProvider {
	
	val log:Logger = LoggerFactory.getLogger(BasicAuthProvider::class.java)
	
	@Value("&{auth.basic.username}")
	lateinit var username:String
	
	@Value("&{auth.basic.password}")
	lateinit var password:String
	
	override fun authenticate(auth: Authentication) : Authentication {

		log.debug(" >>> BasicAuthProvider - authenticate")

		// TODO: or get userAccount from repository (userAccount needs to implement Authentication)
		// so it can be returned by this method (authenticate)
		// or
		// can be authenticated with external service

		// Auth with BasicAuthFilter
		if (auth is UserAccountAuth) {
			val basicAuth: UserAccountAuth = auth
			val token: String? = basicAuth.credentials

			if (token != null && token.startsWith("Basic")) {
				val base64Credentials: String = token.substring("Basic".length).trim()
				val credentials: String = String(Base64.decodeBase64(base64Credentials),
						Charset.forName("UTF-8"))
				val userPass: List<String> = (credentials as CharSequence).split(":")
				val user: String = userPass.get(0)
				val pass: String = userPass.get(1)
				if (username.equals(user) && password.equals(pass)) {
					val grantedAuths: MutableList<GrantedAuthority>  = mutableListOf<GrantedAuthority>()
					grantedAuths.add(SimpleGrantedAuthority("ROLE_USER"))
					basicAuth.setAuthorities(grantedAuths)
					basicAuth.isAuthenticated = true
				} else {
					throw BadCredentialsException("Could not find user: " + user)
				}
			} else {
				auth.setAuthenticated(false)
			}

		// Auth with httpBasic()
		} else if (auth is UsernamePasswordAuthenticationToken) {
			val user: String = auth.getPrincipal().toString()
			val pass: String = auth.getCredentials().toString()

			log.debug(">>> httpBasic username:" + user)
			log.debug(">>> httpBasic password:" + pass)

			if (username.equals(user) && password.equals(pass)) {
				val grantedAuths: MutableList<GrantedAuthority>  = mutableListOf<GrantedAuthority>()
				grantedAuths.add(SimpleGrantedAuthority("ROLE_USER"))

				return UsernamePasswordAuthenticationToken(username, null, grantedAuths)

			} else {
				throw BadCredentialsException("Could not find user: " + user)
			}

		}
		return auth
	}
	
	override fun supports(authentication: Class<*>) : Boolean {
//		return authentication.equals(UserAccountAuth::class.java)
//		return UserAccountAuth::class.java.isAssignableFrom(authentication)
		if(UserAccountAuth::class.java.isAssignableFrom(authentication) ||
				UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)) {
			return true
		} else {
			return false
		}
	}
}