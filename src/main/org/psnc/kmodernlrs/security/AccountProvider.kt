package org.psnc.kmodernlrs.security

interface AccountProvider {
	fun getUserAccount(id: String) : UserAccount?
	fun getAllUserAccounts() : Collection<UserAccount>?
	fun createUserAccount(userAccount: UserAccount) : Boolean
	fun updateUserAccount(userAccount: UserAccount) : Boolean 
}