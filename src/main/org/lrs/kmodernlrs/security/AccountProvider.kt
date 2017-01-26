package org.lrs.kmodernlrs.security

import java.time.LocalDate

interface AccountProvider {
	fun getUserAccount(username: String) : UserAccount?
	fun getAllUserAccounts() : List<UserAccount>?
	fun createUserAccount(userAccount: UserAccount) : Boolean
	fun updateUserAccount(userAccount: UserAccount)
	fun deleteUserAccount(userAccount: UserAccount) : Boolean
	fun setUserAccountRole(userAccount: UserAccount, role: String)
	fun setUserAccountEmail(userAccount: UserAccount, email: String)
	fun userAccountActive(userAccount: UserAccount, active: Boolean)
	fun setToken(userAccount: UserAccount, token: String, tokenExpirationDate: LocalDate)
	fun tokenValid(userAccount: UserAccount) : Boolean
}