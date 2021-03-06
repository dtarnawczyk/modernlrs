package org.lrs.kmodernlrs.controllers.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@ConditionalOnProperty(name=arrayOf("auth.type"), havingValue="basic")
@Component
@Primary
class BasicAuthProvider : AuthenticationProvider {
	
	val log: Logger = LoggerFactory.getLogger(BasicAuthProvider::class.java)
	
	@Value("&{auth.basic.username}")
	lateinit var username:String
	
	@Value("&{auth.basic.password}")
	lateinit var password:String

	@Value("&{auth.basic.database}")
	lateinit var dataSource:String


	@Autowired lateinit var accountProvider: AccountProvider
	
	override fun authenticate(auth: Authentication) : Authentication {

		log.debug(" >>> BasicAuthProvider - authenticate")

		// TODO: or get userAccount from repository (userAccount needs to implement Authentication)
		// so it can be returned by this method (authenticate)
		// or
		// can be authenticated with external service

		// Auth with BasicAuthFilter
		/*if (auth is UserAccountAuth) {
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
		} elseif (auth is UsernamePasswordAuthenticationToken) {*/
			val user: String = auth.principal.toString()
			val pass: String = auth.credentials.toString()

			log.debug(">>> httpBasic username: $user")
			log.debug(">>> httpBasic password: $pass")

		if(dataSource == "database") {
			val userAccount = accountProvider.getUserAccount(user)
			if (userAccount != null) {
				if (userAccount.active) {
					if (userAccount.password.equals(pass)) {
						log.debug(">>> User exists and is active <<<")
						val grantedAuths: MutableList<GrantedAuthority> = mutableListOf()
//						grantedAuths.add(SimpleGrantedAuthority("ROLE_USER"))
						grantedAuths.add(SimpleGrantedAuthority("ROLE_" + userAccount.role.toUpperCase()))
						return UsernamePasswordAuthenticationToken(userAccount, userAccount.password, grantedAuths)
					} else {
						throw BadCredentialsException("Wrong password")
					}
				} else {
					throw BadCredentialsException("User not active: " + user)
				}
			} else {
				throw BadCredentialsException("Could not find user: " + user)
			}
		} else {
			if (username.equals(user) && password.equals(pass)) {
				val grantedAuths: MutableList<GrantedAuthority>  = mutableListOf()
				grantedAuths.add(SimpleGrantedAuthority("ROLE_USER"))
				return UsernamePasswordAuthenticationToken(username, null, grantedAuths)
			} else {
				throw BadCredentialsException("Could not find user: " + user)
			}
		}

//		}
//		return auth
	}
	
	override fun supports(authentication: Class<*>) : Boolean {
//		return authentication.equals(UserAccountAuth::class.java)
//		return UserAccountAuth::class.java.isAssignableFrom(authentication)
		return (UserAccountAuth::class.java.isAssignableFrom(authentication) ||
				UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication))
	}
}