package org.lrs.kmodernlrs.security

import org.lrs.kmodernlrs.services.UserAccountService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UserAccountProvider : AccountProvider {

	@Autowired lateinit var accountService: UserAccountService

	val log: Logger = LoggerFactory.getLogger(UserAccountProvider::class.java)
	
	override fun getUserAccount(username: String) : UserAccount? =
			accountService.getUserAccountByUsername(username)
	
	override fun getAllUserAccounts() : List<UserAccount>? = accountService.getAllUserAccounts()
	
	override fun createUserAccount(userAccount: UserAccount) : Boolean {
		accountService.createUserAccount(userAccount)
		return accountService.exists(userAccount)
	}
	
	override fun updateUserAccount(userAccount: UserAccount) {
		accountService.updateUserAccount(userAccount)
	}

	override fun deleteUserAccount(userAccount: UserAccount) : Boolean {
		accountService.deleteUserAccount(userAccount)
		return !accountService.exists(userAccount)
	}

	override fun setUserAccountRole(userAccount: UserAccount, role: String) {
		userAccount.role = role
		accountService.updateUserAccount(userAccount)
	}

	override fun userAccountActive(userAccount: UserAccount, active: Boolean) {
		userAccount.active = active
		accountService.updateUserAccount(userAccount)
	}

	override fun setUserAccountEmail(userAccount: UserAccount, email: String) {
		userAccount.email = email
		accountService.updateUserAccount(userAccount)
	}

	override fun setToken(userAccount: UserAccount, token: String, tokenExpirationDate: LocalDate) {
		userAccount.token = token
		userAccount.tokenExpirationDate = tokenExpirationDate
	}

	override fun tokenValid(userAccount: UserAccount) : Boolean {
		val currentDate: LocalDate = LocalDate.now()
		val expirationDate = userAccount.tokenExpirationDate
		if(expirationDate != null ) {
			return expirationDate.isBefore(currentDate)
		} else {
			return false
		}
	}
}