package org.psnc.kmodernlrs.security

import org.springframework.security.authentication.AuthenticationProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.beans.factory.annotation.Autowired
import org.psnc.kmodernlrs.repo.Repository
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
	
	val log = LoggerFactory.getLogger(BasicAuthProvider::class.java)
	
	//TODO: can be used as a UserAccount repo
	@Autowired lateinit var repo: Repository
	
	@Value("&{auth.basic.username}")
	lateinit var userName:String
	
	@Value("&{auth.basic.password}")
	lateinit var password:String
	
	override fun authenticate(auth: Authentication) : Authentication {
		var basicAuth: UserAccountAuth = auth as UserAccountAuth
		var token: String? = basicAuth.getCredentials().toString()
		
		if(token != null && token.startsWith("Basic")){
			var base64Credentials: String = token.substring("Basic".length).trim()
			var credentials: String  = String(Base64.decodeBase64(base64Credentials),
					Charset.forName("UTF-8"))
			var userPass : List<String> = (credentials as CharSequence).split(":")
			
			var user: String = userPass.get(0)
			var pass: String = userPass.get(1)
			
			if(userName.equals(user) && password.equals(pass)){
				basicAuth.setAuthenticated(true)
			} else {
				throw BadCredentialsException("Could not find user: " + user)
			}
			
			// TODO: or get userAccount from repository (userAccount needs to implement Authentication
			// so it can be returned by this method (authenticate)
			// or
			// can be authenticated with external service
			
		} else {
			auth.setAuthenticated(false)
		}
		return auth
	}
	
	override fun supports(authentication: Class<*>) : Boolean {
		return authentication.equals(UserAccountAuth::class.java)
//		return UserAccountAuth::class.java.isAssignableFrom(authentication)
	}
}