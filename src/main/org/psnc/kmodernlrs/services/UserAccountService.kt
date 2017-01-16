package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.security.UserAccount

interface UserAccountService {
    fun createUserAccount(user: UserAccount)
    fun updateUserAccount(user: UserAccount)
    fun getUserAccountByUsername(username: String) : UserAccount?
    fun getAllUserAccounts() : List<UserAccount>?
    fun deleteUserAccount(user: UserAccount) : Boolean
    fun exists(user: UserAccount) : Boolean
    fun getUserAccountCount() : Long
}
