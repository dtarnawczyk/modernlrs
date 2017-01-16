package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.repository.RepositoryCustomImpl
import org.psnc.kmodernlrs.security.UserAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
open class UserAccountServiceImpl : UserAccountService {

    @Autowired
    lateinit var repoCustom: RepositoryCustomImpl

    override fun createUserAccount(user: UserAccount) {
        repoCustom.create(user)
    }

    override fun updateUserAccount(user: UserAccount) {
        repoCustom.update(user, UserAccount::class.java)
    }

    override fun getUserAccountByUsername(username: String) : UserAccount? {
        val attributes = hashMapOf<String, String>("name" to username)
        return repoCustom.fimdByAttrs(attributes, UserAccount::class.java) as UserAccount
    }

    override fun getAllUserAccounts(): List<UserAccount>? {
        val userAccounts: List<UserAccount>?
            = repoCustom.findAll(UserAccount::class.java)?.filterIsInstance<UserAccount>()
        return userAccounts
    }

    override fun deleteUserAccount(user: UserAccount) : Boolean {
        repoCustom.deleteById(user.id, UserAccount::class.java)
        return !(repoCustom.exists(user.id, UserAccount::class.java))
    }

    override fun exists(user: UserAccount): Boolean
            = repoCustom.exists(user.id, UserAccount::class.java)

    override fun getUserAccountCount() : Long
            = repoCustom.getCount(UserAccount::class.java)

}