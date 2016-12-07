package org.psnc.kmodernlrs.security

import org.springframework.stereotype.Component

@Component
class UserAccountProvider : AccountProvider {
	
	override fun getUserAccount(id: String) : UserAccount?{
		return null
	}
	
	override fun getAllUserAccounts() : Collection<UserAccount>?{
		return null
	}
	
	override fun createUserAccount(userAccount: UserAccount) : Boolean {
		return false
	}
	
	override fun updateUserAccount(userAccount: UserAccount) : Boolean {
		return false
	}
}